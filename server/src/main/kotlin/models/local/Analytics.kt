package models.local

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import supports.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Analytics(
    val type: String,
    val remoteHost: String,
    @Serializable(LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime
)

object AnalyticsTable : Table() {
    val id = integer("id").autoIncrement()
    val type = text("request")
    val remoteHost = text("remoteHost")
    val createdAt = datetime("createdAt").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}