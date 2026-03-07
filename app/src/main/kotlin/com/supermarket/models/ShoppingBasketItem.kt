package com.supermarket.models

data class ShoppingBasketItem(
    val basketItemId: Int,
    val basketId: Int,
    val productId: Int,
    val quantityAdded: Int,
    val price: Double
)