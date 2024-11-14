package com.example.urdeli.domain.model

data class DeliItem(
    val id: Int,
    val name: String,
    val unit: String,
    val quantity: Int,
    val cost: Double
)
