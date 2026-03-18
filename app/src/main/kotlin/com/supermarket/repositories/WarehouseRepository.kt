package com.supermarket.repositories

import com.supermarket.models.Warehouse
import java.sql.Connection
import java.util.UUID
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

class WarehouseRepository(private val connection: Connection) {

    fun getWarehouses(): List<Warehouse> {
        val sql = "SELECT * FROM warehouse"
        val stmt = connection.prepareStatement(sql).use { stmt ->
            val results = stmt.executeQuery()
            val warehouses = mutableListOf<Warehouse>()

            while (results.next()) {
                warehouses.add(
                    Warehouse(
                        results.getInt("warehouse_id"),
                        results.getString("name"),
                        results.getString("location")
                    )
                )
            }
            return warehouses
        }
    }

}