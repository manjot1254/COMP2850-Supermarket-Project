package com.supermarket
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    // Calls class to run seed into database schema
    DatabaseInitialiser.initialise()

    // Runs Ktor server
    embeddedServer(Netty, port = 8080) {
    routing {
        get("/") {
            call.respondText("Ktor test")
        }
    }
    }.start(wait = true)
    
}
