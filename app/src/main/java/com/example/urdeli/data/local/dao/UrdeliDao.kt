package com.example.urdeli.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.urdeli.data.local.entity.DeliItemEntity

@Dao
interface UrdeliDao {
    @Query("""
        SELECT * FROM deli_items 
        WHERE (LOWER(name) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == name)
        AND department_id = :departmentId 
    """)
    suspend fun getDeliItems(departmentId: Int, query: String): List<DeliItemEntity>

    @Query("""
        SELECT * FROM deli_items
        """)
    suspend fun getAllDeliItems(): List<DeliItemEntity>
}