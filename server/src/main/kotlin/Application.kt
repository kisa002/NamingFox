import dao.analytics.AnalyticsDAO
import dao.analytics.AnalyticsDAOImpl
import dao.error_log.ErrorLogDAO
import dao.error_log.ErrorLogDAOImpl
import dao.naming.NamingDAO
import dao.naming.NamingDAOImpl
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import kotlinx.serialization.json.Json
import module.apiModule
import module.dataModule
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin
import plugins.configureRouting
import repository.OpenApiRepository
import repository.OpenApiRepositoryImpl

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(DefaultHeaders) {
        header("Content-Type", "application/json")
    }

    DatabaseFactory.init()

    koin {
        modules(apiModule, dataModule)
    }

    val namingDao: NamingDAO by inject<NamingDAOImpl>()
    val errorLogDAO: ErrorLogDAO by inject<ErrorLogDAOImpl>()
    val analyticsDAO: AnalyticsDAO by inject<AnalyticsDAOImpl>()
    val openApiRepository: OpenApiRepository by inject<OpenApiRepositoryImpl>()

    configureRouting(
        clientVersion = Version.client,
        namingDao = namingDao,
        errorLogDAO = errorLogDAO,
        analyticsDAO = analyticsDAO,
        openApiRepository = openApiRepository
    )
}
