import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
    application
}

group = "de.hbch.traewelling"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}

dependencies {
    implementation(platform("io.ktor:ktor-bom:2.1.3"))
    implementation("io.ktor", "ktor-server-netty")
    implementation("io.ktor", "ktor-server-auth")
    implementation("io.ktor", "ktor-server-resources")
    implementation("io.ktor", "ktor-server-html-builder")

    implementation("io.ktor", "ktor-client-okhttp")
    implementation("io.ktor", "ktor-client-content-negotiation")
    implementation("io.ktor", "ktor-serialization-kotlinx-json")

    implementation("dev.schlaubi", "stdx-envconf", "1.2.1")

    implementation("org.slf4j", "slf4j-simple", "2.0.4")
}

application {
    mainClass.set("de.hbch.traewelling.auth_service.ServerKt")
}
