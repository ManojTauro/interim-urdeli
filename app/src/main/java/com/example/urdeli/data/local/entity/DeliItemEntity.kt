package com.example.urdeli.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "deli_items"
)
data class DeliItemEntity(
    val name: String,
    val unit: String?,
    val cost: Double,
    @PrimaryKey @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "department_id") val departmentId: Int,
)
