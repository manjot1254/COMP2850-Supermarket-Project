package com.supermarket.routes

import com.supermarket.repositories.ProductRepository
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

        val html = if (products.isEmpty()) {
            "<p>No results found</p>"
        } else {
            products.joinToString("") {
                """
                <div class="product">
                    <p>${it.name} - £${it.price}</p>
                    <button 
                        hx-post="/basket/add/${it.productId}"
                        hx-target="#basket-count">
                        Add to basket
                    </button>
                </div>
                """
            }
        }

        call.respondText(html, ContentType.Text.Html)
    }
}
