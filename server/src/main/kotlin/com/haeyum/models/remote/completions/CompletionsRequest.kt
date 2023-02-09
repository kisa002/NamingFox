package com.haeyum.models.remote.completions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletionsRequest(
    val model: String,
    val prompt: String,
    val temperature: Float,
//    @SerialName("max_tokens")
    val max_tokens: Int
)