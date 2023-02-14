package api.model

import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val finish_reason: String,
    val index: Int,
    //    val logprobs: Any,
    val text: String
)