package se.martinkero

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import se.martinkero.entities.Todo
import se.martinkero.entities.TodoTable
import se.martinkero.services.TodoService
import java.util.*
import kotlin.test.*

@Suppress("unused")
class ApplicationTest {

    private val testUUID = "121588cb-737d-4a77-a628-2d4723fdce6a"

    private fun setup(application: Application) {
        application.configureRouting()
        configureDatabase()
        transaction {
            TodoTable.deleteAll()
        }
    }

    @Test
    fun testUUID() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/api/uuid").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val uuidRegex = """\b[0-9a-f]{8}\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\b[0-9a-f]{12}\b""".toRegex()
                assertTrue(uuidRegex.matches(response.content.orEmpty()))
            }
        }
    }

    @Test
    fun testCreate() {
        withTestApplication({
            setup(this)
        }) {
            handleRequest(HttpMethod.Post, "/api/${testUUID}") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""
                    {content: "Testing create todo"}
                """.trimIndent())
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val todos = TodoService.get(UUID.fromString(testUUID))
                assertFalse(todos.isEmpty())
                assertEquals("Testing create todo", todos.first().content)
            }
        }
    }

    @Test
    fun testGet() {
        withTestApplication({
            setup(this)

            TodoService.create(Todo(
                owner = UUID.fromString(testUUID),
                content = "Testing get todo",
                completed = false
            ))
            TodoService.create(Todo(
                owner = UUID.fromString(testUUID),
                content = "Testing get todo 2",
                completed = false
            ))

        }) {
            handleRequest(HttpMethod.Get, "/api/${testUUID}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
                val todos = Json.decodeFromString<List<Todo>>(response.content!!)
                assertTrue(todos.size == 2)
                assertTrue(todos.first().content == "Testing get todo")
            }
        }
    }

    @Test
    fun testUpdate() {
        var id: Int? = null
        withTestApplication({
            setup(this)

            id = TodoService.create(Todo(
                owner = UUID.fromString(testUUID),
                content = "Testing update todo",
                completed = false
            ))
        }) {
            handleRequest(HttpMethod.Put, "/api/${testUUID}/${id}") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""
                    {content: "Testing update todo 2", completed: true}
                """.trimIndent())
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val todos = TodoService.get(UUID.fromString(testUUID))
                assertFalse(todos.isEmpty())
                val first = todos.first()
                assertEquals("Testing update todo 2", first.content)
                assertTrue(first.completed)
            }
        }
    }

    @Test
    fun testDelete() {
        var id: Int? = null
        withTestApplication({
            setup(this)

            id = TodoService.create(Todo(
                owner = UUID.fromString(testUUID),
                content = "Testing delete todo",
                completed = false
            ))
        }) {
            handleRequest(HttpMethod.Delete, "/api/${testUUID}/${id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val todo = TodoService.get(id!!)
                assertNull(todo)
            }
        }
    }
}
