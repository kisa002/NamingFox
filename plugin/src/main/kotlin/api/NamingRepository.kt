package api

import api.model.Response
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import models.NamingRequest
import models.NamingResponse

object NamingRepository {
    suspend fun getNaming(original: String, type: String, language: String) = KtorClient.client.post("http://127.0.0.1:8080/naming") {
        setBody(
            NamingRequest(original = original, type = type, language = language)
        )
        contentType(ContentType.Application.Json)
    }.body<NamingResponse>().result
}