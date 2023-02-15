package models.naming

import kotlinx.serialization.Serializable

@Serializable
data class NamingRequest(
    val original: String,
    val type: String,
    val language: String
)