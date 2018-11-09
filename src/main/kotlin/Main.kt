import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.*

data class PostSnippet(val snippet: PostSnippet.Text) {
    data class Text(val text: String)
}

data class Snippet(val text: String)

val snippets = Collections.synchronizedList(mutableListOf(
        Snippet("hello"),
        Snippet("world")
))

fun main(args: Array<String>) {
    val server = embeddedServer(
            Netty,
            watchPaths = listOf("api"),
            port = 8080,
            module = Application::module)
    server.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT) // Pretty Prints the JSON
        }
    }
    routing {
        route("snippets") {
            get {
                call.respond(mapOf("snippets" to synchronized(snippets) { snippets.toList() }))
            }
            post {
                val post = call.receive<PostSnippet>()
                snippets += Snippet(post.snippet.text)
                call.respond(mapOf("OK" to true))
            }
        }
    }

}
