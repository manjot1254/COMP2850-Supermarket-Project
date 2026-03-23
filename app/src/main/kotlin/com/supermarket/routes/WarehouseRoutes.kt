package com.supermarket.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

fun Route.warehouseRoutes() {

    // Warehouse route
        get("/warehouse") {
            call.respondFile(java.io.File("src/main/resources/warehouse.html"))
        }

}