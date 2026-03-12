package com.supermarket.repositories

import com.supermarket.models.Order
import java.sql.Connection

class OrderRepository(private val connection: Connection) {

    fun getOrderById(id: Int): Order? {
    val sql = "SELECT * FROM orders WHERE order_id = ?"
    connection.prepareStatement(sql).use { stmt ->
        stmt.setInt(1, id)
        val results = stmt.executeQuery()
        return if (results.next()) {
            Order(
                results.getInt("order_id"),
                results.getInt("user_id"),
                results.getInt("address_id"),
                results.getInt("list_id"),
                results.getDouble("total_price"),
                results.getString("delivery_slot"),
                results.getString("order_status"),
                results.getString("created_at"),
                results.getString("updated_at")
            )
        } else null
    }
    }

    fun getOrdersByUser(id: Int): List<Order> {
    val orders = mutableListOf<Order>()
    connection.prepareStatement("SELECT * FROM orders WHERE user_id = ?").use { stmt ->
        stmt.setInt(1, id)
        val results = stmt.executeQuery()
        while (results.next()) {
            orders.add(
                Order(
                    results.getInt("order_id"),
                    results.getInt("user_id"),
                    results.getInt("address_id"),
                    results.getInt("list_id"),
                    results.getDouble("total_price"),
                    results.getString("delivery_slot"),
                    results.getString("order_status"),
                    results.getString("created_at"),
                    results.getString("updated_at")
                )
            )
        }
    }
    return orders
    }

// Add update order status etc. and Order testing
}

