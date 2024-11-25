package com.example.urdeli.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey @ColumnInfo(name = "department_id") val departmentId: Int,
    val name: String,
) : BaseEntity()
