package se.martinkero.entities

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import se.martinkero.UUIDSerializer
import java.util.*

object TodoTable : IntIdTable() {
    val owner = uuid("uuid")
    val content = varchar("content", 1000)
    val completed = bool("completed")
//    val index = integer("index")
}

@Serializable
data class Todo(
    val id: Int? = null,
    @Serializable(with = UUIDSerializer::class)
    val owner: UUID,
    var content: String,
    var completed: Boolean,
) {
    companion object {
        fun fromResultRow(resultRow: ResultRow) = Todo(
            id = resultRow[TodoTable.id].value,
            owner = resultRow[TodoTable.owner],
            content = resultRow[TodoTable.content],
            completed = resultRow[TodoTable.completed]
        )

        fun populateUpdateBuilder(builder: UpdateBuilder<Int>, todo: Todo) {
            builder[TodoTable.owner] = todo.owner
            builder[TodoTable.content] = todo.content
            builder[TodoTable.completed] = todo.completed
        }
    }
}

@Serializable
data class TodoDTO(
    val id: Int? = null,
    @Serializable(with = UUIDSerializer::class)
    val owner: UUID? = null,
    val content: String? = null,
    var completed: Boolean? = null,
)