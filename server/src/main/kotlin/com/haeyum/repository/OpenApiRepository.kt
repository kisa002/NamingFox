package com.haeyum.repository

interface OpenApiRepository {
    suspend fun fetchNaming(original: String, language: String, type: String): String?
}