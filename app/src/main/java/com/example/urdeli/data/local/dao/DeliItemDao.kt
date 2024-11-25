package com.example.urdeli.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.urdeli.data.local.entity.DeliItemEntity
import com.example.urdeli.shared.DeliItemWithQuantity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliItemDao {
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

    @Query("""
        SELECT di.*,
        COALESCE(se.quantity, 0) AS quantity
        FROM deli_items di
        LEFT JOIN stocktake_entries se 
        ON (di.item_id = se.item_id AND 
            se.stocktake_id = :stockTakeId) 
        WHERE (LOWER(name) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == name) AND
            di.department_id = :departmentId
        ORDER BY di.item_id
    """
    )
    fun getDeliItemsWithQuantity(
        stockTakeId: Int,
        departmentId: Int,
        query: String
    ): Flow<List<DeliItemWithQuantity>>
}