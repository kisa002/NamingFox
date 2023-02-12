package com.haeyum

import com.haeyum.dao.analytics.AnalyticsDAO
import com.haeyum.dao.analytics.AnalyticsDAOImpl
import com.haeyum.dao.error_log.ErrorLogDAO
import com.haeyum.dao.error_log.ErrorLogDAOImpl
import com.haeyum.dao.naming.NamingDAO
import com.haeyum.dao.naming.NamingDAOImpl
import com.haeyum.module.apiModule
import com.haeyum.module.dataModule
import com.haeyum.plugins.configureRouting
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
    DatabaseFactory.init()

    koin {
        modules(apiModule, dataModule)
    }

    val namingDao: NamingDAO by inject<NamingDAOImpl>()
    val errorLogDAO: ErrorLogDAO by inject<ErrorLogDAOImpl>()
    val analyticsDAO: AnalyticsDAO by inject<AnalyticsDAOImpl>()
    val openApiRepository by inject<OpenApiRepositoryImpl>()

    configureRouting(
        namingDao = namingDao,
        errorLogDAO = errorLogDAO,
        analyticsDAO = analyticsDAO,
        openApiRepository = openApiRepository
    )
}
