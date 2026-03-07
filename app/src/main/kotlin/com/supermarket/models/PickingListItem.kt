package com.supermarket.models

data class PickingListItem(
    val listItemId: Int,
    val listId: Int,
    val userId: Int,
    val productId: Int,
    val pickedAt : String,
    val quantityPicked: Int,
    val quantityRequired: Int,
    val itemStatus: String
)