package com.supermarket.models

data class Delivery(
    val deliveryId: Int,
    val warehouseId: Int,
    val userId: Int,
    val supplier: String,
    val receivedAt: String
)