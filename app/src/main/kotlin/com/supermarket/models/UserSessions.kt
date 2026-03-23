package com.supermarket.models

data class UserSession(
    val sessionId: String,
    val userId: Int,
    val ipAddress: String,
    val createdAt: String,
    val expiresAt: String
)