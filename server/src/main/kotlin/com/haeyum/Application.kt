package com.haeyum

import com.haeyum.module.apiModule
import com.haeyum.module.dataModule
import com.haeyum.plugins.configureRouting
import com.haeyum.repository.OpenApiRepository
import com.haeyum.repository.OpenApiRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    koin {
        modules(apiModule, dataModule)
    }

    val openApiRepository by inject<OpenApiRepositoryImpl>()

    DatabaseFactory.init()
    configureRouting(openApiRepository = openApiRepository)
}
