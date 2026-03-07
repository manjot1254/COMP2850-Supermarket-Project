package com.supermarket.repositories.tests
import com.supermarket.repositories.UserRepository
import com.supermarket.models.User
import com.supermarket.DatabaseConnection
import com.supermarket.DatabaseInitialiser
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import io.kotest.core.spec.style.StringSpec

// Add testing for individual users i.e. customers, workers and managers
class UserRepositoryTest : StringSpec({
    DatabaseInitialiser.initialise()
    val connection = DatabaseConnection.getConnection()
    val userRepository = UserRepository(connection)

    "User details should all be correctly fetched by email"{
        val user = userRepository.getUserByEmail("joe@warehouse.com")
        assertNotNull(user)
        assertEquals("Joe", user.firstName)
        assertEquals("Smith", user.lastName)
        assertEquals("07111111111", user.phoneNumber)
        assertEquals("hashedpassword", user.passwordHash)
        assertEquals("worker", user.role)
        assertEquals("2026-03-07", user.createdAt)
    }

    "Non-existent user should return null" {
        val user = userRepository.getUserByEmail("nonexistent@warehouse.com")
        assertNull(user)
    }

    "User with null last name should be handled correctly" {
        val user = userRepository.getUserByEmail("liam@warehouse.com")
        assertNotNull(user)
        assertEquals("Liam", user.firstName)
        assertNull(user.lastName)
        assertEquals("07666666666", user.phoneNumber)
        assertEquals("hashedpassword", user.passwordHash)
        assertEquals("worker", user.role)
        assertEquals("2026-03-07", user.createdAt)
    }
})