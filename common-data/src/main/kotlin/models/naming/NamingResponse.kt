package models.naming

import kotlinx.serialization.Serializable
import models.BaseResponse

@Serializable
data class NamingResponse(
    override val code: Int,
    override val message: String,
    override val result: NamingData? = null
): BaseResponse<NamingData>