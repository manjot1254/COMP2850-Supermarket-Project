package com.supermarket.routes

import io.ktor.server.pebble.PebbleContent
import io.pebbletemplates.pebble.loader.ClasspathLoader
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
        val loggedInUser = userRepository.getLoggedInUser(call, userSessionRepository)

        call.respond(PebbleContent("login", mapOf(
            "isLoggedIn" to (loggedInUser?.firstName != null),
            "loggedInUser" to loggedInUser,
            "loginMessage" to "", "registerMessage" to ""
        ) as Map<String, Any>))
    }

    // Login route
    post("/login") {
        val params = call.receiveParameters()
        val email = params["email"] ?: ""
        val password = params["password"] ?: ""

        val user = userRepository.getUserByEmail(email)

        var message = ""
        if (user != null && user.passwordHash == password) {
            val sessionId = UUID.randomUUID().toString()

            userSessionRepository.createSession(user.userId, sessionId)

            call.response.cookies.append(
            Cookie("SESSION_ID", sessionId)
            )
            message = "Login successful"
        } else {
            message = "Invalid email or password"
        }

        call.respond(PebbleContent("fragments/login-result", mapOf("loginMessage" to message)))
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
        val loggedInUser = userRepository.getLoggedInUser(call, userSessionRepository)

        call.respond(PebbleContent("fragments/navbar", mapOf("loggedInUser" to loggedInUser as Any)))
    }
}