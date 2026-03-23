package com.supermarket.routes

import io.ktor.server.pebble.PebbleContent
import io.pebbletemplates.pebble.loader.ClasspathLoader
import com.supermarket.repositories.WarehouseRepository
import com.supermarket.repositories.StockRepository
import com.supermarket.models.Warehouse
import com.supermarket.models.User
import com.supermarket.models.Stock
import com.supermarket.models.ProductStock
import com.supermarket.models.StockChange
import com.supermarket.utils.StockChangeLogger
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*
import io.ktor.http.*

fun Route.warehouseRoutes (warehouseRepository: WarehouseRepository, stockRepository: StockRepository) {
    
    // In-memory storage for pending changes per warehouse
    val pendingChangesByWarehouse = mutableMapOf<Int, MutableMap<Int, StockChange>>()

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

    post("/warehouse/change") {
        val parameters = call.receiveParameters()
        val warehouseParam = parameters["warehouse"]
        val warehouseId = warehouseParam?.toIntOrNull()
        
        if (warehouseId == null) {
            call.respondText("No warehouse specified", status = HttpStatusCode.BadRequest)
            return@post
        }

        val productId = parameters["productId"]?.toIntOrNull()
        val productName = parameters["productName"]
        val change = parameters["change"]?.toIntOrNull()

        if (productId == null || productName == null || change == null) {
            call.respondText("Invalid parameters", status = HttpStatusCode.BadRequest)
            return@post
        }

        // Get or create the changes map for this warehouse
        val changesForWarehouse = pendingChangesByWarehouse.getOrPut(warehouseId) { mutableMapOf() }

        // Update or create the stock change
        val existingChange = changesForWarehouse[productId]
        if (existingChange != null) {
            // Update existing change
            changesForWarehouse[productId] = existingChange.copy(
                change = existingChange.change + change
            )
        } else {
            // Create new change
            changesForWarehouse[productId] = StockChange(
                productId = productId,
                productName = productName,
                change = change,
                warehouseId = warehouseId
            )
        }

        // Return updated changes list
        val pendingChanges = changesForWarehouse.values.toList()
        call.respond(PebbleContent("fragments/warehouse-changes", mapOf("pendingChanges" to pendingChanges as Any)))
    }

    post("/warehouse/apply") {
        val parameters = call.receiveParameters()
        val warehouseParam = parameters["warehouse"]
        val warehouseId = warehouseParam?.toIntOrNull()

        if (warehouseId == null) {
            call.respondText("No warehouse specified", status = HttpStatusCode.BadRequest)
            return@post
        }

        val changesForWarehouse = pendingChangesByWarehouse[warehouseId]
        
        if (changesForWarehouse == null || changesForWarehouse.isEmpty()) {
            call.respondText("No changes to apply", status = HttpStatusCode.BadRequest)
            return@post
        }

        val changesList = changesForWarehouse.values.toList()

        // Apply all changes to the database
        for (change in changesList) {
            stockRepository.updateStockQuantity(change.productId, warehouseId, change.change)
        }

        // Log changes to CSV file
        StockChangeLogger.logChanges(warehouseId, changesList)

        // Clear the pending changes for this warehouse
        pendingChangesByWarehouse.remove(warehouseId)

        // Get updated warehouse data
        val updatedProducts = stockRepository.getStockByWarehouse(warehouseId)

        // Return a response that indicates success and includes updated data
        call.respondText("<div class=\"success-message\">✓ Changes applied successfully!</div>", contentType = io.ktor.http.ContentType.Text.Html)
    }

}

