package com.supermarket.repositories

import com.supermarket.models.UserFavourites
import java.sql.Connection

class UserFavouritesRepository(private val connection: Connection) {
    fun addFavourite(userId: Int, productId: Int) {
        val sql = """
            INSERT OR IGNORE INTO user_favourites (user_id, product_id, created_at)
            VALUES (?, ?, datetime('now'))
        """

        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, userId)
            stmt.setInt(2, productId)
            stmt.executeUpdate()
        }
    }

}