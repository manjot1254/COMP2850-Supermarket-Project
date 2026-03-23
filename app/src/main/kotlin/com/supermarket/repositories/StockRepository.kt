package com.supermarket.repositories
import com.supermarket.models.Stock
import com.supermarket.models.ProductStock
import java.sql.Connection

class StockRepository(private val connection: Connection) {

    fun getStockByWarehouse(warehouseId: Int): List<ProductStock> {
        val sql = """
            SELECT p.product_id, p.name, s.quantity
            FROM stock s
            JOIN products p ON s.product_id = p.product_id
            WHERE s.warehouse_id = ?
        """
        
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, warehouseId)
            val results = stmt.executeQuery()
            val products = mutableListOf<ProductStock>()

            while (results.next()) {
                products.add(
                    ProductStock(
                        results.getInt("product_id"),
                        results.getString("name"),
                        results.getInt("quantity"),
                    )
                )
            }
            return products
        }
    }

    fun getWarehouseProducts(warehouseId: Int): List<Stock> {
        val sql = """
            SELECT products.name, stock.quantity
            FROM stock
            JOIN products ON stock.product_id = products.product_id
            WHERE stock.warehouse_id = ?
        """

        return connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, warehouseId)
            val results = stmt.executeQuery()
            val products = mutableListOf<Stock>()

            while (results.next()) {
                products.add(
                    Stock(
                        results.getInt("stock_id"),
                        results.getInt("product_id"),
                        results.getInt("warehouse_id"),
                        results.getInt("quantity"),
                        results.getString("updated_at")
                    )
                )
            }
            products
        }
    }

    fun updateStockQuantity(productId: Int, warehouseId: Int, changeAmount: Int) {
        val sql = """
            UPDATE stock
            SET quantity = quantity + ?, updated_at = CURRENT_TIMESTAMP
            WHERE product_id = ? AND warehouse_id = ?
        """
        
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, changeAmount)
            stmt.setInt(2, productId)
            stmt.setInt(3, warehouseId)
            stmt.executeUpdate()
        }
    }
}
