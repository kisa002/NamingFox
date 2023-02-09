package com.haeyum.models.remote.completions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    @SerialName("finish_reason")
    val finishReason: String,
    val index: Int,
    val text: String
)