package com.supermarket

import com.supermarket.repositories.ProductRepository
import com.supermarket.repositories.UserRepository
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
            // Serve the main login page
            get("/") {
                call.respondFile(java.io.File("src/main/resources/login.html"))
            }

            // Login route
            post("/login") {
                val params = call.receiveParameters()
                val email = params["email"] ?: ""
                val password = params["password"] ?: ""

                val user = userRepository.getUserByEmail(email)

                if (user != null && user.passwordHash == password) {
                    call.respondText("Login successful")
                } else {
                    call.respondText("Invalid email or password")
                }
            }

            // Register route
            post("/register") {
                val params = call.receiveParameters()
                val email = params["email"] ?: ""
                val password = params["password"] ?: ""
                val confirmPassword = params["confirmPassword"] ?: ""
                val firstName = params["firstName"] ?: ""
                val lastName = params["lastName"] ?: ""
                val phoneNumber = params["phoneNumber"] ?: ""

                if (password != confirmPassword) {
                    call.respondText("Passwords do not match")
                    return@post
                }

                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser != null) {
                    call.respondText("Email already registered")
                    return@post
                }

                userRepository.createUser(
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    password
                )

                call.respondText("User registered successfully")
            }
        }
    }.start(wait = true)
}
