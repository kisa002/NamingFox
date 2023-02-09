package com.haeyum.models.remote.completions

import kotlinx.serialization.Serializable

@Serializable
data class CompletionsResponse(
    val choices: List<Choice>,
    val usage: Usage
)