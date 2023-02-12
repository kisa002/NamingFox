package models.local

import supports.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

@Serializable
data class Naming(
    val id: Int,
    val original: String,
    val naming: String,
    val type: String,
    val language: String,
    @Serializable(LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime
)

object Namings : Table() {
    val id = integer("id").autoIncrement()
    val original = varchar("original", 128)
    val naming = varchar("naming", 128)
    val type = varchar("type", 8)
    val language = varchar("language", 12)
    val createdAt = datetime("createdAt").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}