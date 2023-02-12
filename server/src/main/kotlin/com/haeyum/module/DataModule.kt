package com.haeyum.module

import com.haeyum.dao.analytics.AnalyticsDAOImpl
import com.haeyum.dao.error_log.ErrorLogDAOImpl
import com.haeyum.dao.naming.NamingDAOImpl
import com.haeyum.repository.OpenApiRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::OpenApiRepositoryImpl)
    singleOf(::NamingDAOImpl)
    singleOf(::ErrorLogDAOImpl)
    singleOf(::AnalyticsDAOImpl)
}