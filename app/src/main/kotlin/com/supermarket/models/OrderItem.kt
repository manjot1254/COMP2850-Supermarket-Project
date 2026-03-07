package com.supermarket.models

data class OrderItem(
    val orderItemId: Int,
    val orderId: Int,
    val productId: Int,
    val quantityOrdered: Int,
    val price: Double,
    val substitutedProductId: Int?
)