package com.supermarket.repositories

import com.supermarket.models.ShoppingBasket
import java.sql.Connection

class ShoppingBasketRepository(private val connection: Connection) {

    fun getBasketByUserId(userId: Int): ShoppingBasket? {
        val sql = "SELECT * FROM shopping_baskets WHERE user_id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, userId)
            val results = stmt.executeQuery()
            return if (results.next()) {
                ShoppingBasket(
                    results.getInt("basket_id"),
                    results.getInt("user_id"),
                    results.getString("created_at"),
                    results.getString("updated_at")
                )
            } else null
        }
    }

    fun addToBasket(userId: Int, productId: Int) {
    val sql = """
        INSERT INTO basket_items (user_id, product_id, quantity)
        VALUES (?, ?, 1)
        ON CONFLICT(user_id, product_id)
        DO UPDATE SET quantity = quantity + 1
    """

    connection.prepareStatement(sql).use { stmt ->
        stmt.setInt(1, userId)
        stmt.setInt(2, productId)
        stmt.executeUpdate()
    }
    }

}