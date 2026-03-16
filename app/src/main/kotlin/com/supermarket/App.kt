package com.supermarket

import com.supermarket.repositories.ProductRepository
import com.supermarket.repositories.UserRepository
import com.supermarket.routes.homeRoutes
import com.supermarket.routes.authRoutes
import com.supermarket.routes.warehouseRoutes
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    // Calls class to run seed into database schema
    DatabaseInitialiser.initialise()

    val connection = DatabaseConnection.getConnection()
    val userRepository = UserRepository(connection)

    println(userRepository.getUserByEmail("joe@warehouse.com"))

    // Runs Ktor server
    embeddedServer(Netty, port = 8080) {
        routing {
            homeRoutes()

            authRoutes(userRepository)

            warehouseRoutes()
        }
    }.start(wait = true)
}
