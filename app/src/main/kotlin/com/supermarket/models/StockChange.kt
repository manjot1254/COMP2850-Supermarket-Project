package com.supermarket.models

data class StockChange(
    val productId: Int,
    val productName: String,
    val change: Int,
    val warehouseId: Int
)
