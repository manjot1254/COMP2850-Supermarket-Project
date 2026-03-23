package com.supermarket.utils

import com.supermarket.models.StockChange
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StockChangeLogger {
    private val dataDir = File("data")
    private val changesFile = File(dataDir, "stock_changes.csv")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init {
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }
        // Create CSV header if file doesn't exist
        if (!changesFile.exists()) {
            changesFile.writeText("Timestamp,Warehouse ID,Product ID,Product Name,Change Amount,Staff ID\n")
        }
    }

    fun logChanges(warehouseId: Int, changes: List<StockChange>, staffId: String = "PLACEHOLDER") {
        val timestamp = LocalDateTime.now().format(dateFormatter)
        val lines = changes.map { change ->
            "$timestamp,$warehouseId,${change.productId},${change.productName},${change.change},$staffId"
        }
        
        synchronized(changesFile) {
            changesFile.appendText(lines.joinToString("\n") + "\n")
        }
    }
}
