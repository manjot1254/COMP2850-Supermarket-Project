package com.supermarket

data class Stock(
    val stockId: Int,
    val productId: Int,
    val warehouseId: Int,
    val quantity: Int,
    val updatedAt: String?
)