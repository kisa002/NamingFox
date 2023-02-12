package models.common

import kotlinx.serialization.Serializable
import models.common.BaseResponse
import models.common.NamingData

@Serializable
data class NamingResponse(
    override val code: Int,
    override val message: String,
    override val result: NamingData? = null
): BaseResponse<NamingData>