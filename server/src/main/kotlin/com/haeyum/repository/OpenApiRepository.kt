package com.haeyum.repository

interface OpenApiRepository {
    suspend fun fetchNaming(original: String, type: String, language: String): String?
}