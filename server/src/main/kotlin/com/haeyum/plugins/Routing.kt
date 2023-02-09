package com.haeyum.plugins

import com.haeyum.dao.NamingDAO
import com.haeyum.dao.NamingDAOImpl
import com.haeyum.models.common.NamingData
import com.haeyum.models.common.NamingResponse
import com.haeyum.repository.OpenApiRepository
import com.haeyum.repository.OpenApiRepositoryImpl
import com.haeyum.supports.toJsonString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting(
    namingDao: NamingDAO = NamingDAOImpl(),
    openApiRepository: OpenApiRepository = OpenApiRepositoryImpl()
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/namings") {
            runCatching {
                call.respond(HttpStatusCode.OK, namingDao.allNamings().toJsonString())
            }.onFailure {
                println(it)
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        post("/naming") {
            runCatching {
                val (original, language, type) = call.receiveParameters().let {
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
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        NamingResponse(code = -1, message = "이름을 짓지 못하였습니다.").toJsonString()
                    )
                }
            }.onFailure {
                call.respond(
                    HttpStatusCode.OK,
                    NamingResponse(code = -2, message = it.message.orEmpty()).toJsonString()
                )
            }
        }
    }
}
