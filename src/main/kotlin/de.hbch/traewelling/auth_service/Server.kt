package de.hbch.traewelling.auth_service

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable

private const val AUTH_NAME = "TRWL"

@Resource("")
@Serializable
class API {
    @Resource("auth")
    @Serializable
    class Auth(val api: API = API()) {
        @Resource("callback")
        @Serializable
        class Callback(val auth: Auth = Auth())
    }
}

fun main() {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    embeddedServer(Netty, host = Config.HOSTNAME, port = Config.PORT) {
        install(Resources)
        install(Authentication) {
            oauth(AUTH_NAME) {
                urlProvider = { application.buildUrl(API.Auth.Callback()) }
                providerLookup = {
                    OAuthServerSettings.OAuth2ServerSettings(
                        name = AUTH_NAME,
                        authorizeUrl = Config.TRAEWELLING_URL.modify { path("oauth", "authorize") },
                        accessTokenUrl = Config.TRAEWELLING_URL.modify { path("oauth", "token") },
                        requestMethod = HttpMethod.Post,
                        clientId = Config.TRAEWELLING_CLIENT_ID,
                        clientSecret = Config.TRAEWELLING_CLIENT_SECRET
                    )
                }

                this.client = client
            }
        }

        routing {
            authenticate(AUTH_NAME) {
                get<API.Auth> {
                    // A user should never get redirected to here
                    call.respond("Well this is awkward!")
                }

                get<API.Auth.Callback> {
                    val principal = call.principal<OAuthAccessTokenResponse.OAuth2>()
                        ?: throw BadRequestException("Missing OAuth data")

                    val url = Config.DEEPLINK.modify {
                        path("auth", "login")
                        parameters["access_token"] = principal.accessToken
                        parameters["refresh_token"] = principal.refreshToken.toString()
                        parameters["expires_in"] = principal.expiresIn.toString()
                    }

                    call.respondHtml {
                        head {
                            title { +"Traewelling - Login" }
                        }

                        body {
                            script {
                                unsafe {
                                    val intent =
                                        URLBuilder(url).apply {
                                            protocol = URLProtocol("traewelldroid", 80)
                                        }.buildString()

                                    //language=JavaScript
                                    raw("""setTimeout(() => {window.location.href = "$intent"}, 250)""".trimIndent())
                                }
                            }

                            p {
                                +"If you are not getting redirected please click "
                                a {
                                    href = url
                                    +"here"
                                }
                            }
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}
