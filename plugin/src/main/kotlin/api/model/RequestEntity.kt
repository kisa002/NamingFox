package api.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestEntity(val model: String, val prompt: String, val temperature: Float, val max_tokens: Int)