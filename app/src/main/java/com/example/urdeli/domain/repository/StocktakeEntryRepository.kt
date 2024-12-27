package com.example.urdeli.domain.repository

import com.example.urdeli.domain.model.StocktakeEntry
import com.example.urdeli.shared.StocktakeEntryWithDeliItem

interface StocktakeEntryRepository {
    suspend fun updateQuantity(stockTakeEntry: StocktakeEntry)
    suspend fun getALlStockEntriesWithDeliItems(
        stocktakeId: Int,
    ): List<StocktakeEntryWithDeliItem>
}