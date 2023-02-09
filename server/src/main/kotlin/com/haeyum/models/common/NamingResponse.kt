package com.haeyum.models.common

import kotlinx.serialization.Serializable

@Serializable
data class NamingResponse(
    override val code: Int,
    override val message: String,
    override val result: NamingData? = null
): BaseResponse<NamingData>