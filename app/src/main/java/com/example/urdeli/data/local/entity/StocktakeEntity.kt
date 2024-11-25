package com.example.urdeli.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_takes")
data class StocktakeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "store_name") val storeName: String,
    @ColumnInfo(name = "store_location") val storeLocation: String,
) : BaseEntity() {}