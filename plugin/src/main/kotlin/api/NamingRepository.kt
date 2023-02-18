package api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import models.naming.NamingRequest
import models.naming.NamingResponse
import module.ApiModule

object NamingRepository {
    suspend fun getNaming(original: String, type: String, language: String) = ApiModule.client.post(ClientConfig.SERVER_URL + "/naming") {
        setBody(
            NamingRequest(original = original, type = type, language = language)
        )
        contentType(ContentType.Application.Json)
    }.body<NamingResponse>()
}