package com.example.urdeli.data.local.entity

import androidx.room.ColumnInfo
import java.util.Date

abstract class BaseEntity {
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    var createdAt: Date? = null

    @ColumnInfo(name = "modified_at")
    var modifiedAt: Date? = null
}