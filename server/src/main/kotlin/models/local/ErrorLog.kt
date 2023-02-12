package models.local

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class ErrorLog(
    val request: String?,
    val response: String?,
    val reason: String?
)

object ErrorLogs : Table() {
    val id = integer("id").autoIncrement()
    val request = text("request").nullable()
    val response = text("response").nullable()
    val reason = text("reason").nullable()

    override val primaryKey = PrimaryKey(id)
}