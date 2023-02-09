package com.haeyum.dao

import com.haeyum.models.local.ErrorLog

interface ErrorLogDAO {
    suspend fun addErrorLog(request: String?, response: String?, reason: String?)
    suspend fun loadAll(): List<ErrorLog>
}