package com.supermarket.repositories
import com.supermarket.models.User
import java.sql.Connection

class UserRepository(private val connection: Connection) {

    fun getUserByEmail(email: String): User? {
        val sql = "SELECT * FROM users WHERE email_address = ?"
        val stmt = connection.prepareStatement(sql).use { stmt ->
        stmt.setString(1, email)

        val results = stmt.executeQuery()

        return if (results.next()) {
            User(
                results.getInt("user_id"),
                results.getString("first_name"),
                results.getString("last_name"),
                results.getString("email_address"),
                results.getString("phone_number"),
                results.getString("password_hash"),
                results.getString("role"),
                results.getString("created_at")
            )
        } else null
    }
}
}