package com.supermarket.routes

import io.ktor.server.pebble.PebbleContent
import io.pebbletemplates.pebble.loader.ClasspathLoader
import com.supermarket.repositories.WarehouseRepository
import com.supermarket.repositories.StockRepository
import com.supermarket.models.Warehouse
import com.supermarket.models.User
import com.supermarket.models.Stock
import com.supermarket.models.ProductStock
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*
import io.ktor.http.content.*

fun Route.warehouseRoutes (warehouseRepository: WarehouseRepository, stockRepository: StockRepository) {

    // Warehouse route
    get("/warehouse") {
        call.respond(PebbleContent("warehouse", mapOf()))
    }

    get("/warehouse/data") {
        val warehouseParam = call.request.queryParameters["warehouse"]
        val warehouseId = warehouseParam?.toIntOrNull()

        val products = if (warehouseId != null) {
            stockRepository.getStockByWarehouse(warehouseId)
        } else {
        emptyList()
        }

        call.respond(PebbleContent("fragments/warehouse-results", mapOf("products" to products as Any)))
    }

}
