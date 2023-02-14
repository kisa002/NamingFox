package api.model

import api.model.Choice
import api.model.Usage
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