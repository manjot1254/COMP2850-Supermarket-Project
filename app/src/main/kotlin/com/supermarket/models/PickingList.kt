package com.supermarket

data class PickingList(
    val listId: Int,
    val warehouseId: Int,
    val userId: Int,
    val listStatus: String,
    val createdAt: String,
    val updatedAt: String?
)