package com.supermarket.repositories.tests

import com.supermarket.repositories.UserRepository
import com.supermarket.DatabaseConnection
import com.supermarket.DatabaseInitialiser
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    @BeforeAll
    fun setup() {
        DatabaseInitialiser.initialise()
        val connection = DatabaseConnection.getConnection()
        userRepository = UserRepository(connection)
    }

    @Test
    fun `getUserByEmail returns correct user`() {

        val user = userRepository.getUserByEmail("joe@warehouse.com")

        assertNotNull(user)
        assertEquals("Joe", user!!.firstName)
        assertEquals("Smith", user.lastName)
        assertEquals("worker", user.role)
    }

    @Test
    fun `getUserByEmail returns manager`() {

        val user = userRepository.getUserByEmail("emma@manager.com")

        assertNotNull(user)
        assertEquals("Emma", user!!.firstName)
        assertEquals("manager", user.role)
    }

    @Test
    fun `getUserByEmail returns customer`() {

        val user = userRepository.getUserByEmail("aisha@email.com")

        assertNotNull(user)
        assertEquals("customer", user!!.role)
    }

    @Test
    fun `getUserByEmail returns null for unknown email`() {

        val user = userRepository.getUserByEmail("notfound@email.com")

        assertNull(user)
    }

    @Test
    fun `multiple warehouse workers exist`() {

        val worker1 = userRepository.getUserByEmail("joe@warehouse.com")
        val worker2 = userRepository.getUserByEmail("liam@warehouse.com")

        assertNotNull(worker1)
        assertNotNull(worker2)

        assertEquals("worker", worker1!!.role)
        assertEquals("worker", worker2!!.role)
    }
}