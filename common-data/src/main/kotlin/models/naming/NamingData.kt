package models.naming

import kotlinx.serialization.Serializable

@Serializable
data class NamingData(
    val original: String,
    val naming: String,
    val type: String,
    val language: String
)