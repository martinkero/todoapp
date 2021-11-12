package se.martinkero.services

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import se.martinkero.entities.Todo
import se.martinkero.entities.TodoTable
import java.util.*

object TodoService {

    fun get(id: Int): Todo? {
        return transaction {
            val query = TodoTable.select { TodoTable.id eq id }
            val resultRow = query.firstOrNull() ?: return@transaction null
            Todo.fromResultRow(resultRow)
        }
    }

    fun get(owner: UUID): List<Todo> {
        return transaction {
            TodoTable.select { TodoTable.owner eq owner }.mapNotNull { Todo.fromResultRow(it) }.toList()
        }
    }

    fun create(todo: Todo): Int {
        return transaction {
            TodoTable.insertAndGetId {
                Todo.populateUpdateBuilder(it, todo)
            }.value
        }
    }

    fun update(todo: Todo) {
        transaction {
            TodoTable.update({ TodoTable.id eq todo.id }) {
                Todo.populateUpdateBuilder(it, todo)
            }
        }
    }

    fun delete(todo: Todo): Boolean {
        return transaction {
            TodoTable.deleteWhere { TodoTable.id eq todo.id } > 0
        }
    }

}