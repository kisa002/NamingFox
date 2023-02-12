package dao.error_log

import DatabaseFactory.dbQuery
import models.local.ErrorLog
import models.local.ErrorLogs
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class ErrorLogDAOImpl : ErrorLogDAO {
    private fun resultRowToErrorLog(row: ResultRow) = ErrorLog(
        request = row[ErrorLogs.request],
        response = row[ErrorLogs.response],
        reason = row[ErrorLogs.reason],
    )

    override suspend fun addErrorLog(request: String?, response: String?, reason: String?) = dbQuery {
        val insertStatement = ErrorLogs.insert {
            it[ErrorLogs.request] = request
            it[ErrorLogs.response] = response
            it[ErrorLogs.reason] = reason
        }
    }

    override suspend fun loadAll(): List<ErrorLog> = dbQuery {
        ErrorLogs.selectAll().map(::resultRowToErrorLog)
    }
}