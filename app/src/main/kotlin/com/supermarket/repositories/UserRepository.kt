package com.supermarket.repositories

import com.supermarket.models.User
import com.supermarket.repositories.UserSessionRepository
import java.sql.Connection
import java.util.UUID
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

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

    // New createUser function
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
            stmt.setString(2, lastName ?: "")
            stmt.setString(3, email)
            stmt.setString(4, phoneNumber)
            stmt.setString(5, passwordHash)

            stmt.executeUpdate()
        }
    }
    
    fun getLoggedInUser(call: ApplicationCall, userSessionRepository: UserSessionRepository): User? {
        val sessionId = call.request.cookies["SESSION_ID"] ?: return null
        return userSessionRepository.getUserBySessionId(sessionId)
    }

}