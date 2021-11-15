package se.martinkero

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import se.martinkero.entities.TodoTable
import se.martinkero.routing.todo

fun main() {
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
        configureRouting()
        configureDatabase()
    }.start(wait = true)
}

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route("/api/") {
            todo()
        }
    }
}

fun configureDatabase() {

    Database.connect("jdbc:postgresql://todoapp-postgres:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres")

    transaction {
        SchemaUtils.create(TodoTable)
    }
}
