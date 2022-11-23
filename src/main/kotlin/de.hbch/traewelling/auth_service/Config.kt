package de.hbch.traewelling.auth_service

import io.ktor.http.*
import dev.schlaubi.envconf.Config as EnvironmentConfig

object Config : EnvironmentConfig() {
    val URL by getEnv(Url("http://localhost:8080"), ::Url)
    val HOSTNAME by getEnv("0.0.0.0")
    val PORT by getEnv(8080, String::toInt)

    val TRAEWELLING_URL by getEnv(Url("https://traewelling.de"), ::Url)
    val TRAEWELLING_CLIENT_ID by environment
    val TRAEWELLING_CLIENT_SECRET by environment

    val DEEPLINK by getEnv(Url("https://app.traewelldroid.de"), ::Url)
}
