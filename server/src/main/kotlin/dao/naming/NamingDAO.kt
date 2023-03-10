package dao.naming

import models.local.Naming

interface NamingDAO {
    suspend fun addNewNaming(original: String, naming: String, type: String, language: String): Naming?
    suspend fun allNamings(): List<Naming>
    suspend fun findNaming(original: String, type: String, language: String): Naming?
    suspend fun existsNaming(original: String, type: String, language: String): Boolean
}