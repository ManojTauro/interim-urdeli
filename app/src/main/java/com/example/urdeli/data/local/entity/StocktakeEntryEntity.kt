package com.example.urdeli.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocktake_entries")
data class StocktakeEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "department_id") val departmentId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "stocktake_id") val stocktakeId: Int,
) : BaseEntity()
