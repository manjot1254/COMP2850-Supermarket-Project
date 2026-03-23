package com.supermarket

import com.supermarket.repositories.ProductRepository
import com.supermarket.repositories.UserRepository
import com.supermarket.repositories.UserSessionRepository
import com.supermarket.repositories.WarehouseRepository
import com.supermarket.repositories.StockRepository

import com.supermarket.routes.homeRoutes
import com.supermarket.routes.authRoutes
import com.supermarket.routes.warehouseRoutes
import com.supermarket.routes.productRoutes

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.pebble.*
import io.ktor.http.content.*
import io.pebbletemplates.pebble.loader.ClasspathLoader
import io.ktor.server.http.content.staticResources

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
    val productRepository = ProductRepository(connection)
    val userSessionRepository = UserSessionRepository(connection)
    val warehouseRepository = WarehouseRepository(connection)
    val stockRepository = StockRepository(connection)
    
    // Runs Ktor server
    embeddedServer(Netty, port = 8080) {
        // Sets up Pebble template engine for HTML templates
        install(Pebble) {
        loader(ClasspathLoader().apply {
            prefix = "templates"
            suffix = ".peb"
        })
        }

        routing {
            staticResources("/", "static")

            homeRoutes(userRepository, userSessionRepository)   

            authRoutes(userRepository, userSessionRepository)

            warehouseRoutes(warehouseRepository, stockRepository)

            productRoutes(productRepository)
        }
    }.start(wait = true)
}
