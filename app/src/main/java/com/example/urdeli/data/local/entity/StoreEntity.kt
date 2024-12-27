package com.example.urdeli.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey(autoGenerate = true) val stocktakeId: Int = 0,
    @ColumnInfo(name = "store_name") val storeName: String,
    @ColumnInfo(name = "store_location") val storeLocation: String,
    @ColumnInfo(name = "supervisor") val supervisor: String
) : BaseEntity() {}