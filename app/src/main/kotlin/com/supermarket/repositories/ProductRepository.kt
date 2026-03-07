package com.supermarket.repositories
import com.supermarket.models.Product
import java.sql.Connection

class ProductRepository(private val conn: Connection) {

    fun getProductById(id: Int): Product? {
        val sql = "SELECT * FROM products WHERE product_id = ?"
        val stmt = conn.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, id)
            val results = stmt.executeQuery()
        return if (results.next()) {
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
        } else null
    }
}

    fun getProducts(): List<Product> {
        val products = mutableListOf<Product>()

        val sql = "SELECT * FROM products"
        val stmt = conn.prepareStatement(sql).use { stmt ->
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
}
