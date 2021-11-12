package se.martinkero.routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import se.martinkero.entities.Todo
import se.martinkero.entities.TodoDTO
import se.martinkero.services.TodoService
import java.util.*

fun Route.todo() {

    get("/uuid") {
        call.respondText(UUID.randomUUID().toString())
    }

    get("/{uuid}") {

        val uuid = call.parameters["uuid"]

        if (uuid.isNullOrEmpty()) {
            call.respond(HttpStatusCode.NotFound)
            return@get
        }

        val parsedUUID = UUID.fromString(uuid)

        val todos = TodoService.get(parsedUUID)

        call.respond(todos)
    }

    post("/{uuid}") {

        val uuid = call.parameters["uuid"]
        val todoDTO = call.receive<TodoDTO>()

        if (uuid.isNullOrEmpty()) {
            call.respond(HttpStatusCode.NotFound)
            return@post
        }

        if (todoDTO.content.isNullOrEmpty()) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val parsedUUID = UUID.fromString(uuid)

        val todoID = TodoService.create(Todo(
            owner = parsedUUID,
            content = todoDTO.content,
            completed = false
        ))

        val todo = TodoService.get(todoID)

        if (todo == null) {
            call.respond(HttpStatusCode.InternalServerError)
            return@post
        }

        call.respond(todo)
    }

    put("/{uuid}/{id}") {

        val uuid = call.parameters["uuid"]
        val id = call.parameters["id"]?.toInt()

        if (uuid.isNullOrEmpty() || id == null) {
            call.respond(HttpStatusCode.NotFound)
            return@put
        }

        val todoDTO = call.receive<TodoDTO>()

        if (todoDTO.content.isNullOrEmpty()) {
            call.respond(HttpStatusCode.BadRequest)
            return@put
        }

        val todo = TodoService.get(id)

        if (todo == null) {
            call.respond(HttpStatusCode.NotFound)
            return@put
        }

        if (todo.owner.toString() != uuid) {
            call.respond(HttpStatusCode.Unauthorized)
            return@put
        }

        todo.content = todoDTO.content
        todo.completed = todoDTO.completed ?: false

        TodoService.update(todo)

        call.respond(todo)
    }

    delete("/{uuid}/{id}") {

        val uuid = call.parameters["uuid"]
        val id = call.parameters["id"]?.toInt()

        if (uuid.isNullOrEmpty() || id == null) {
            call.respond(HttpStatusCode.NotFound)
            return@delete
        }

        val todo = TodoService.get(id)

        if (todo == null) {
            call.respond(HttpStatusCode.NotFound)
            return@delete
        }

        if (todo.owner.toString() != uuid) {
            call.respond(HttpStatusCode.Unauthorized)
            return@delete
        }

        TodoService.delete(todo)

        call.respond(todo)
    }
}