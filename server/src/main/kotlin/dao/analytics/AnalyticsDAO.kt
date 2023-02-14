package dao.analytics

import models.local.Analytics

interface AnalyticsDAO {
    suspend fun addAnalytics(type: String, remoteHost: String)
    suspend fun loadAll(): List<Analytics>
}