package api

import io.ktor.client.call.*
import io.ktor.client.request.*
import models.version.VersionResponse
import module.ApiModule

object VersionRepository {
    suspend fun fetchVersion(): VersionResponse = ApiModule.client.get(ClientConfig.SERVER_URL + "/version").body()
}