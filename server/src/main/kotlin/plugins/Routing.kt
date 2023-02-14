package plugins

import dao.analytics.AnalyticsDAO
import dao.error_log.ErrorLogDAO
import dao.naming.NamingDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import models.NamingData
import models.NamingRequest
import models.NamingResponse
import repository.OpenApiRepository
import supports.toJsonString

fun Application.configureRouting(
    namingDao: NamingDAO,
    errorLogDAO: ErrorLogDAO,
    analyticsDAO: AnalyticsDAO,
    openApiRepository: OpenApiRepository
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/errorLogs") {
            kotlin.runCatching {
                call.respond(HttpStatusCode.OK, errorLogDAO.loadAll().toJsonString())
            }.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
                errorLogDAO.addErrorLog(request = call.parameters.toString(), reason = it.toString())
            }
        }

        get("/namings") {
            runCatching {
                call.respond(HttpStatusCode.OK, namingDao.allNamings().toJsonString())
            }.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
                errorLogDAO.addErrorLog(request = call.parameters.toString(), reason = it.toString())
            }
        }

        post("/naming") {
            val request = if (call.request.contentType() == ContentType.Application.Json)
                call.receive<NamingRequest>()
            else
                call.receiveParameters().let {
                    NamingRequest(
                        original = it.getOrFail("original"),
                        type = it.getOrFail("type"),
                        language = it.getOrFail("language")
                    )
                }

            runCatching {
                val naming = namingDao.findNaming(
                    original = request.original,
                    type = request.type,
                    language = request.language
                )?.naming
                    ?: openApiRepository.fetchNaming(
                        original = request.original,
                        type = request.type,
                        language = request.language
                    )

                naming?.let {
                    NamingData(
                        original = request.original,
                        naming = it,
                        type = request.type,
                        language = request.language
                    )
                }
            }.onSuccess { namingData ->
                if (namingData != null) {
                    call.respond(
                        HttpStatusCode.OK,
                        NamingResponse(code = 0, message = "", result = namingData).toJsonString()
                    )
                    if (!namingDao.existsNaming(
                            original = namingData.original,
                            type = namingData.type,
                            language = namingData.language
                        )
                    ) {
                        namingDao.addNewNaming(
                            original = namingData.original,
                            naming = namingData.naming,
                            type = namingData.type,
                            language = namingData.language
                        )
                    }
                    analyticsDAO.addAnalytics(type = namingData.type, call.request.origin.remoteHost)
                } else {
                    NamingResponse(code = -1, message = "이름을 짓지 못하였습니다.").toJsonString().let { response ->
                        call.respond(
                            HttpStatusCode.OK,
                            response
                        )
                        errorLogDAO.addErrorLog(
                            request = request.toString(),
                            response = response,
                            reason = it.toString()
                        )
                    }
                }
            }.onFailure {
                NamingResponse(code = -2, message = it.message.orEmpty()).toJsonString().let { response ->
                    call.respond(
                        HttpStatusCode.OK,
                        response
                    )
                    errorLogDAO.addErrorLog(
                        request = request.toString(),
                        response = response,
                        reason = it.toString()
                    )
                }
            }
        }

        get("analytics") {
            kotlin.runCatching {
                call.respond(HttpStatusCode.OK, analyticsDAO.loadAll().toJsonString())
            }.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
                errorLogDAO.addErrorLog(request = call.parameters.toString(), reason = it.toString())
            }
        }
    }
}
