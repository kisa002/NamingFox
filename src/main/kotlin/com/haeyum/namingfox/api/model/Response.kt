package com.haeyum.namingfox.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)