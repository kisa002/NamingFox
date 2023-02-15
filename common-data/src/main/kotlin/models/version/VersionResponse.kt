package models.version

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    val version: String
)