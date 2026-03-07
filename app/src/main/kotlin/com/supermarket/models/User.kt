package com.supermarket

data class User(
    val userId: Int,
    val firstName: String,
    val lastName: String?,
    val emailAddress: String,
    val phoneNumber: String,
    val passwordHash: String,
    val role: String,
    val createdAt: String
)