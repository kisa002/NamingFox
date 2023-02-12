package api.model

import kotlinx.serialization.Serializable

@Serializable
data class Usage(
//    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)