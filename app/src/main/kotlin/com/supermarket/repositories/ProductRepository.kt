package com.supermarket.repositories
import com.supermarket.models.Product
import java.sql.Connection

class ProductRepository(private val conn: Connection) {

    fun getProducts(): List<Product> {
        val products = mutableListOf<Product>()

        val sql = "SELECT * FROM products"
        val stmt = conn.prepareStatement(sql)
        val results = stmt.executeQuery()

        while (results.next()) {
            products.add(
                Product(
                    results.getInt("product_id"),
                    results.getInt("category_id"),
                    results.getString("name"),
                    results.getString("location"),
                    results.getString("description"),
                    results.getString("barcode"),
                    results.getDouble("price"),
                    results.getDouble("volume_per_unit"),
                    results.getString("image_url"),
                    results.getString("created_at")
                )
            )
        }

        return products
    }
}