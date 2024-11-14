package com.example.urdeli.domain.model

data class DeliItem(
    val itemId: Int,
    val name: String,
    val unit: String?,
    val cost: Double
)
