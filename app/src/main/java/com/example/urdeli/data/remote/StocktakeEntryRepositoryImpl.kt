package com.example.urdeli.data.remote

import android.content.Context
import com.example.urdeli.data.local.AppDatabase
import com.example.urdeli.data.mapper.toStocktakeEntryEntity
import com.example.urdeli.domain.model.StocktakeEntry
import com.example.urdeli.domain.repository.StocktakeEntryRepository

class StocktakeEntryRepositoryImpl(
    context: Context
) : StocktakeEntryRepository {
    private val db = AppDatabase.getInstance(context)

    private val dao = db.stocktakeEntryDao()
    override suspend fun updateQuantity(stockTakeEntry: StocktakeEntry) {

        val stocktakeEntryEntity = dao.getStockEntry(
            stockTakeEntry.itemId,
            stockTakeEntry.stocktakeId,
            stockTakeEntry.departmentId
        )

        if (stocktakeEntryEntity != null) {
            val newQuantity = stocktakeEntryEntity.quantity + stockTakeEntry.quantity
            dao.updateStockEntry(stocktakeEntryEntity.copy(quantity = newQuantity))
        } else dao.insertStockEntry(stockTakeEntry.toStocktakeEntryEntity())
    }
}