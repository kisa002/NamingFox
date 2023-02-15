package api

import io.ktor.client.call.*
import io.ktor.client.request.*
import models.version.VersionResponse

object VersionRepository {
    suspend fun fetchVersion(): VersionResponse = KtorClient.client.get("http://127.0.0.1:8080/version").body()
}