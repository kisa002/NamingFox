package module

import dao.analytics.AnalyticsDAOImpl
import dao.error_log.ErrorLogDAOImpl
import dao.naming.NamingDAOImpl
import repository.OpenApiRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::OpenApiRepositoryImpl)
    singleOf(::NamingDAOImpl)
    singleOf(::ErrorLogDAOImpl)
    singleOf(::AnalyticsDAOImpl)
}