package com.supermarket
import java.sql.DriverManager
import java.sql.Connection

object DatabaseConnection {
    fun getConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:src/main/resources/db/supermarket_database.db")
    }
}