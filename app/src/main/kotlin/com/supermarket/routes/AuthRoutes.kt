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
import java.time.LocalDateTime

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

        call.respond(PebbleContent("fragments/login-result", mapOf(
            "isLoggedIn" to (message == "Login successful"),
            "loggedInUser" to if (message == "Login successful") user else null,
            "loginMessage" to message
        ) as Map<String, Any>)) 
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

        val existingUser = userRepository.getUserByEmail(email)
        var message = ""

        if (password != confirmPassword) {
            message = "Passwords do not match"
        }
        else if (existingUser != null) {
            message = "Email already registered"
        }
        else{       
            // Need to hash password and update role logic
            userRepository.createUser(
                firstName,
                lastName,
                email,
                phoneNumber,
                password,
                "Customer",
                LocalDateTime.now().toString()
            )
            message = "Registration successful"
        }

        call.respond(PebbleContent("fragments/register-result", mapOf(
            "isRegistered" to (message == "Registration successful"),
            "registerMessage" to message
        ) as Map<String, Any>)) 

    }

    get("/navbar") {
        val loggedInUser = userRepository.getLoggedInUser(call, userSessionRepository)

        call.respond(PebbleContent("fragments/navbar", mapOf("loggedInUser" to loggedInUser as Any)))
    }
}