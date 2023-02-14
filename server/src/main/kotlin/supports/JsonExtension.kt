package supports

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> T.toJsonString() = Json.encodeToString<T>(this)