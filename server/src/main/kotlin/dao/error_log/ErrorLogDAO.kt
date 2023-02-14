package dao.error_log

import models.local.ErrorLog

interface ErrorLogDAO {
    suspend fun addErrorLog(request: String? = null, response: String? = null, reason: String?)
    suspend fun loadAll(): List<ErrorLog>
}