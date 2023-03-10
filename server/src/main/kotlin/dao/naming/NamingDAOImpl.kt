package dao.naming

import DatabaseFactory.dbQuery
import models.local.Naming
import models.local.Namings
import org.jetbrains.exposed.sql.*

class NamingDAOImpl : NamingDAO {
    private fun resultRowToNaming(row: ResultRow) = Naming(
        id = row[Namings.id],
        original = row[Namings.original],
        naming = row[Namings.naming],
        type = row[Namings.type],
        language = row[Namings.language],
        createdAt = row[Namings.createdAt]
    )

    override suspend fun addNewNaming(original: String, naming: String, type: String, language: String) = dbQuery {
        val insertStatement = Namings.insert {
            it[Namings.original] = original
            it[Namings.naming] = naming
            it[Namings.type] = type
            it[Namings.language] = language
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNaming)
    }

    override suspend fun allNamings(): List<Naming> = dbQuery {
        Namings.selectAll().map(::resultRowToNaming)
    }

    override suspend fun findNaming(original: String, type: String, language: String): Naming? = dbQuery {
        Namings
            .select { (Namings.original eq original) and (Namings.type eq type) and (Namings.language eq language) }
            .limit(1)
            .map(::resultRowToNaming)
            .singleOrNull()
    }

    override suspend fun existsNaming(original: String, type: String, language: String): Boolean = dbQuery {
        Namings
            .select { (Namings.original eq original) and (Namings.type eq type) and (Namings.language eq language) }
            .singleOrNull() != null
    }
}