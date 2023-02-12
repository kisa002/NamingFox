package models.common

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val original: String,
    val type: String,
    val language: String
)