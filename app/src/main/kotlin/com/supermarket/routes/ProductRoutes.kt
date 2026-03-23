package com.supermarket.routes

import io.ktor.server.pebble.PebbleContent
import io.pebbletemplates.pebble.loader.ClasspathLoader
import com.supermarket.repositories.ProductRepository
import com.supermarket.repositories.UserRepository
import com.supermarket.repositories.UserSessionRepository
import com.supermarket.models.User
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*
import io.ktor.http.ContentType

// TODO: Add images to each item that comes up
fun Route.productRoutes(productRepository: ProductRepository) {
    get("/search") {
        val query = call.request.queryParameters["query"] ?: ""
        val products = productRepository.searchProducts(query)

        call.respond(PebbleContent("fragments/search-results", mapOf("products" to products)))
    }
}
