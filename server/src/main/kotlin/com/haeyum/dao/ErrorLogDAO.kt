package com.haeyum.dao

import com.haeyum.models.local.ErrorLog

interface ErrorLogDAO {
    suspend fun addErrorLog(request: String? = null, response: String? = null, reason: String?)
    suspend fun loadAll(): List<ErrorLog>
}