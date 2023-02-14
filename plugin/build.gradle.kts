val ktor_version: String by project
val kotlin_version: String by project

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.jetbrains.intellij") version "1.12.0"
    id("io.ktor.plugin") version "2.2.3"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "com.haeyum"
version = "1.0-SNAPSHOT"

intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":common-data"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}