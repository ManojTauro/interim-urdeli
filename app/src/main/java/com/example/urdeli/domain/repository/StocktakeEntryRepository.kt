package com.example.urdeli.domain.repository

import com.example.urdeli.domain.model.StocktakeEntry

interface StocktakeEntryRepository {
    suspend fun updateQuantity(stockTakeEntry: StocktakeEntry)
}