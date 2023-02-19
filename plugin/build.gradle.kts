val kotlin_version: String by project
val client_version: String by project

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.jetbrains.intellij") version "1.12.0"
    id("io.ktor.plugin") version "2.2.3"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "com.haeyum"
version = client_version

intellij {
    version.set("2022.1.4")
    type.set("IC")

    updateSinceUntilBuild.set(false)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":common-data"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.test {
    useJUnitPlatform()
}