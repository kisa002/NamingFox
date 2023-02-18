val ktor_version: String by project
val kotlin_version: String by project
val client_version: String by project
val logback_version: String by project
val koin_ktor: String by project

plugins {
    java
    kotlin("jvm") version "1.8.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        // Test
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

        // Coroutine
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

        // Ktor Client
        implementation("io.ktor:ktor-client-core:$ktor_version")
        implementation("io.ktor:ktor-client-cio:$ktor_version")
        implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
        implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
        implementation("ch.qos.logback:logback-classic:$logback_version")

        // Koin
        //    implementation("io.insert-koin:koin-core:$koin_version")
        implementation("io.insert-koin:koin-ktor:$koin_ktor")
        implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")
    }
}

tasks.test {
    useJUnitPlatform()
}