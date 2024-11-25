package com.example.urdeli.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.urdeli.data.local.entity.StocktakeEntryEntity

@Dao
interface StocktakeEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockEntry(entry: StocktakeEntryEntity)
    @Update
    suspend fun updateStockEntry(entry: StocktakeEntryEntity)

    @Query("""
        SELECT stocktake_entries.* 
        FROM stocktake_entries
        WHERE stocktake_entries.item_id = :itemId
            AND stocktake_entries.stocktake_id = :stocktakeId
            AND stocktake_entries.department_id = :departmentId
    """)
    suspend fun getStockEntry(itemId: Int, stocktakeId: Int, departmentId: Int): StocktakeEntryEntity?
}