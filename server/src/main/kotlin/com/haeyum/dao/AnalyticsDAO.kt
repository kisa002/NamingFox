package com.haeyum.dao

import com.haeyum.models.local.Analytics

interface AnalyticsDAO {
    suspend fun addAnalytics(type: String, remoteHost: String)
    suspend fun loadAll(): List<Analytics>
}