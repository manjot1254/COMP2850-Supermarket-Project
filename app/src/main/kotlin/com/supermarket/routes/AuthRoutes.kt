package com.supermarket.routes

import com.supermarket.repositories.UserRepository
import com.supermarket.repositories.UserSessionRepository
import com.supermarket.models.User
import com.supermarket.models.UserSession
import java.util.UUID
import io.ktor.http.ContentType
import io.ktor.http.Cookie
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

fun Route.authRoutes(userRepository: UserRepository, userSessionRepository: UserSessionRepository) {
    get("/login") {
            call.respondFile(java.io.File("src/main/resources/login.html"))
        }

        // Login route
        post("/login") {
            val params = call.receiveParameters()
            val email = params["email"] ?: ""
            val password = params["password"] ?: ""

            val user = userRepository.getUserByEmail(email)

            if (user != null && user.passwordHash == password) {
                val sessionId = UUID.randomUUID().toString()

                userSessionRepository.createSession(user.userId, sessionId)

                call.response.cookies.append(
                Cookie("SESSION_ID", sessionId)
                )

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

    get("/navbar") {
        val user = getLoggedInUser(call, userRepository)

        val html = if (user != null) {
           """<a href="/profile">Profile</a>"""
        } else {
        """<a href="/login">Log in / Register</a>"""
        }   

        call.respondText(html, ContentType.Text.Html)
        }


    fun getLoggedInUser(
    call: ApplicationCall,
    userSessionRepository: UserSessionRepository,
    userRepository: UserRepository
    ): User? {

    val sessionId = call.request.cookies["SESSION_ID"] ?: return null

    val userId = userSessionRepository.getUserBySessionId(sessionId)

    return if (userId != null) userRepository.getUserBySessionId(userId) else null
    
}
}