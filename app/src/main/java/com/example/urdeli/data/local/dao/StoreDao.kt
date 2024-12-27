package com.example.urdeli.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.urdeli.data.local.entity.StoreEntity

@Dao
interface StoreDao {
    @Insert
    suspend fun insertStore(store: StoreEntity)

    @Query("SELECT * FROM stores")
    suspend fun getStores(): List<StoreEntity>
}