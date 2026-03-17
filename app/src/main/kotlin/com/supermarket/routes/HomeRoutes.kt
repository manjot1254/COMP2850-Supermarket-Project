package com.supermarket.routes

import io.ktor.server.pebble.PebbleContent
import io.pebbletemplates.pebble.loader.ClasspathLoader
import com.supermarket.repositories.UserRepository
import com.supermarket.repositories.UserSessionRepository
import com.supermarket.models.User
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

fun Route.homeRoutes(userRepository: UserRepository, userSessionRepository: UserSessionRepository) {

    // Serve main login page 
    get("/") {
        val loggedInUser = userRepository.getLoggedInUser(call, userSessionRepository)

        call.respond(PebbleContent("homepage", mapOf(
            "isLoggedIn" to (loggedInUser?.firstName != null),
            "loggedInUser" to loggedInUser,
            "loginMessage" to "", "registerMessage" to ""
        ) as Map<String, Any>))
    }

}