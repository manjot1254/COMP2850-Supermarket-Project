package com.supermarket

data class Order(
    val orderId: Int,
    val userId: Int,
    val addressId: Int,
    val listId: Int?,
    val totalPrice: Double,
    val deliverySlot: String?,
    val orderStatus: String,
    val createdAt: String,
    val updatedAt: String?
)