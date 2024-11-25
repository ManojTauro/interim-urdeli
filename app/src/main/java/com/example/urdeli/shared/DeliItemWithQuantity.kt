package com.example.urdeli.shared

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.urdeli.data.local.entity.DeliItemEntity

data class DeliItemWithQuantity(
    @Embedded val deliItem: DeliItemEntity,
    @ColumnInfo(name = "quantity") val quantity: Int = 0,
)
