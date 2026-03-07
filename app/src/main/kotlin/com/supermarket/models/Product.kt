package com.supermarket.models

data class Product(
    val productId: Int,
    val categoryId: Int,
    val name: String,
    val location: String,
    val description: String?,
    val barcode: String,
    val price: Double,
    val volumePerUnit: Double,
    val imageUrl: String?,
    val createdAt: String
)