package se.martinkero

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import se.martinkero.entities.TodoTable
import se.martinkero.routing.todo
import java.util.*

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
            get("/uuid") {
                call.respondText(UUID.randomUUID().toString())
            }

            todo()
        }
    }
}

fun configureDatabase() {

    val dbServer = System.getenv("DB_SERVER")
    val dbPort = System.getenv("DB_PORT")
    val dbName = System.getenv("DB_NAME")
    val dbUser = System.getenv("DB_USER")
    val dbPassword = System.getenv("DB_PASSWORD")

    Database.connect("jdbc:postgresql://${dbServer}:${dbPort}/${dbName}",
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword)

    transaction {
        SchemaUtils.create(TodoTable)
    }
}
