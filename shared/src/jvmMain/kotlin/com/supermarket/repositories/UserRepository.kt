package com.supermarket.repositories

import com.supermarket.models.User
import java.sql.Connection

class UserRepository(private val connection: Connection) {

    fun getUserByEmail(email: String): User? {
        val sql = "SELECT * FROM users WHERE email_address = ?"
        return connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, email)
            val results = stmt.executeQuery()
            if (results.next()) {
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

    fun createUser(
        firstName: String,
        lastName: String?,
        email: String,
        phoneNumber: String,
        passwordHash: String
    ) {
        val sql = """
            INSERT INTO users 
            (first_name, last_name, email_address, phone_number, password_hash, role)
            VALUES (?, ?, ?, ?, ?, 'customer')
        """
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, firstName)
            stmt.setString(2, lastName)
            stmt.setString(3, email)
            stmt.setString(4, phoneNumber)
            stmt.setString(5, passwordHash)
            stmt.executeUpdate()
        }
    }
}