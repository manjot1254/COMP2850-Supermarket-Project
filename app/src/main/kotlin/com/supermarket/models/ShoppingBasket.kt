package com.supermarket.models

data class ShoppingBasket(
    val basketId: Int,
    val orderId: Int,
    val createdAt: String,
    val updatedAt: String?
)
