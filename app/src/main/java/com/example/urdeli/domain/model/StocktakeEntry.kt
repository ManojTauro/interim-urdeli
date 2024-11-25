package com.example.urdeli.domain.model

data class StocktakeEntry(
    val id: Int = 0,
    val itemId: Int,
    val quantity: Int,
    val stocktakeId: Int,
    val departmentId: Int
)
