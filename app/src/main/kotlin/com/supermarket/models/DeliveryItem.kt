package com.supermarket.models

data class DeliveryItem(
    val deliveryItemId: Int,
    val deliveryId: Int,
    val product_id: Int,
    val quantity_delivered: Int
)