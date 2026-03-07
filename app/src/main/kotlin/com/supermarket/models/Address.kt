package com.supermarket.models

data class Address(
    val addressId: Int,
    val userId: Int,
    val line_1: String,
    val line_2: String?,
    val town: String,
    val country: String,
    val postcode: String
)