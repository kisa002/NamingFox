package api

import io.ktor.client.call.*
import io.ktor.client.request.*
import models.version.VersionResponse

object VersionRepository {
    suspend fun fetchVersion(): VersionResponse = KtorClient.client.get(ClientConfig.SERVER_URL + "/version").body()
}