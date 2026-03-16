package com.supermarket.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

fun Route.homeRoutes() {

    // TODO: Check if user is logged in and show profile instead of login button - work around role
    // Serve main login page 
    get("/") {
        call.respondFile(java.io.File("src/main/resources/homepage.html"))
    }

}