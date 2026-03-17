package com.supermarket.repositories

import com.supermarket.models.UserSession
import com.supermarket.models.User
import java.sql.Connection
import java.util.UUID
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.content.*

class UserSessionRepository(private val connection: Connection) {

    fun createSession(userId: Int, sessionId: String) {
        val sql = """
            INSERT INTO user_sessions 
            (user_id, session_id, created_at, expires_at)
            VALUES (?, ?, datetime('now'), datetime('now', '+1 day'))
        """
        val stmt = connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, userId)
            stmt.setString(2, sessionId)
            stmt.executeUpdate()
        }
    }

    fun getUserBySessionId(sessionId: String): User? {
        val sql = """
            SELECT u.* FROM users u
            JOIN user_sessions s ON u.user_id = s.user_id
            WHERE s.session_id = ?
        """
        val stmt = connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, sessionId)
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

    fun deleteSession(sessionId: String) {
        val sql = "DELETE FROM user_sessions WHERE session_id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, sessionId)
            stmt.executeUpdate()
        }

    }
}
