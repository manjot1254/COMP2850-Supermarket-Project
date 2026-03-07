package com.supermarket.repositories.tests
import com.supermarket.repositories.ProductRepository
import com.supermarket.models.Product
import com.supermarket.DatabaseConnection
import com.supermarket.DatabaseInitialiser
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import io.kotest.core.spec.style.StringSpec

class ProductRepositoryTest : StringSpec({
    DatabaseInitialiser.initialise()
    val connection = DatabaseConnection.getConnection()
    val productRepository = ProductRepository(connection)

    "Product details should all be correctly fetched by ID"{
        val product = productRepository.getProductById(1)
        assertNotNull(product)
        assertEquals("Bananas", product.name)
        assertEquals("Aisle 1", product.location)
        assertEquals("Fresh bananas", product.description)
        assertEquals("123456789012", product.barcode)
        assertEquals(1.20, product.price)
        assertEquals(1.00, product.volumePerUnit)
        assertEquals("2026-03-07", product.createdAt)
    }

    "Non-existent product should return null" {
        val product = productRepository.getProductById(999)
        assertNull(product)
    }

    "Products with null image_url should be handled correctly" {
        val product = productRepository.getProductById(9)
        assertNotNull(product)
        assertEquals("Milk", product.name)
        assertEquals("Aisle 3", product.location)
        assertNull(product.imageUrl)
        assertEquals("123456789020", product.barcode)
        assertEquals(1.50, product.price)
        assertEquals(1.00, product.volumePerUnit)
        assertEquals("2026-03-07", product.createdAt)
    }

    "Fetching all products should return the correct number of products" {
        val products = productRepository.getProducts()
        assertEquals(30, products.size)
    }
})