package com.supermarket.models

data class UserFavourites(
    val userId: Int,
    val productId: Int,
    val createdAt: String
)