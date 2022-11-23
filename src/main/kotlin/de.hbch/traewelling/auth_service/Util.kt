package de.hbch.traewelling.auth_service

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*

inline fun Url.modify(builder: URLBuilder.() -> Unit) =
    URLBuilder(this).apply(builder).buildString()

inline fun <reified T : Any> Application.buildUrl(resource: T): String {
    val resourcePath = href(resource)

    return Config.URL.modify { encodedPath = resourcePath }
}
