package com.supermarket
import java.sql.DriverManager
import java.io.File

object DatabaseInitialiser {

    fun connect() = DriverManager.getConnection("jdbc:sqlite:src/main/resources/db/supermarket_database.db")

    fun initialise() {
        val connection = connect()
        val statement = connection.createStatement()

        val schema = File("src/main/resources/db/schema.sql").readText()
        val seed = File("src/main/resources/db/seed.sql").readText()

        statement.executeUpdate(schema)
        statement.executeUpdate(seed)

        connection.close()
    }
}