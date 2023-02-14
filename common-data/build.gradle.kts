plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}