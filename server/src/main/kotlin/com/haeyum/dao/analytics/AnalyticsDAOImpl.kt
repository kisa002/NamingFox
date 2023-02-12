package com.haeyum.dao.analytics

import com.haeyum.DatabaseFactory.dbQuery
import com.haeyum.models.local.Analytics
import com.haeyum.models.local.AnalyticsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class AnalyticsDAOImpl : AnalyticsDAO {
    private fun resultRowToAnalytics(row: ResultRow) =
        Analytics(
            type = row[AnalyticsTable.type],
            remoteHost = row[AnalyticsTable.remoteHost],
            createdAt = row[AnalyticsTable.createdAt]
        )

    override suspend fun addAnalytics(type: String, remoteHost: String) = dbQuery {
        val insertStatement = AnalyticsTable.insert {
            it[AnalyticsTable.type] = type
            it[AnalyticsTable.remoteHost] = remoteHost
        }
    }

    override suspend fun loadAll(): List<Analytics> = dbQuery {
        AnalyticsTable.selectAll().map(::resultRowToAnalytics)
    }
}