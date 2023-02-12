package com.haeyum.plugins

import com.haeyum.dao.analytics.AnalyticsDAO
import com.haeyum.dao.analytics.AnalyticsDAOImpl
import com.haeyum.dao.error_log.ErrorLogDAO
import com.haeyum.dao.error_log.ErrorLogDAOImpl
import com.haeyum.dao.naming.NamingDAO
import com.haeyum.dao.naming.NamingDAOImpl
import com.haeyum.models.common.NamingData
import com.haeyum.models.common.NamingResponse
import com.haeyum.repository.OpenApiRepository
import com.haeyum.repository.OpenApiRepositoryImpl
import com.haeyum.supports.toJsonString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting(
    namingDao: NamingDAO = NamingDAOImpl(),
    errorLogDAO: ErrorLogDAO = ErrorLogDAOImpl(),
    analyticsDAO: AnalyticsDAO = AnalyticsDAOImpl(),
    openApiRepository: OpenApiRepository = OpenApiRepositoryImpl()
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
            val parameters = call.receiveParameters()

            runCatching {
                val (original, language, type) = parameters.let {
                    Triple(it.getOrFail("original"), it.getOrFail("language"), it.getOrFail("type"))
                }

                val naming = namingDao.findNaming(original = original, type = type, language = language)?.naming
                    ?: openApiRepository.fetchNaming(original = original, type = type, language = language)

                naming?.let {
                    NamingData(
                        original = original,
                        naming = it,
                        type = type,
                        language = language
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
                            request = parameters.toString(),
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
                        request = parameters.toString(),
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
