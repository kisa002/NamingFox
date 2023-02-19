package repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import models.remote.completions.CompletionsRequest
import models.remote.completions.CompletionsResponse

class OpenApiRepositoryImpl(private val client: HttpClient) : OpenApiRepository {
    override suspend fun fetchNaming(original: String, type: String, language: String) =
        client.post("https://api.openai.com/v1/completions") {
            val prompt = "'$original' is the description of the $type. Name it in extension of $language style without explain.\n"

            println(prompt)
            setBody(
                CompletionsRequest(
                    model = "text-davinci-003",
                    prompt = prompt,
                    temperature = 0f,
                    maxTokens = 100
                )
            )
            bearerAuth(ServerConfig.API_KEY)
            contentType(ContentType.Application.Json)
        }.body<CompletionsResponse>().choices.firstOrNull()?.text?.removePrefix("\n")
}