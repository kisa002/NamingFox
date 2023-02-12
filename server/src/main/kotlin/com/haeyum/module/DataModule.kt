package com.haeyum.module

import com.haeyum.repository.OpenApiRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::OpenApiRepositoryImpl)
}