package com.example.urdeli.domain.repository

import com.example.urdeli.shared.DeliItemWithQuantity
import com.example.urdeli.util.Resource
import kotlinx.coroutines.flow.Flow

interface DeliItemRepository {
    suspend fun getDeliItemsWithQuantity(
        stockTakeId: Int,
        departmentId: Int,
        query: String
    ) : Flow<Resource<List<DeliItemWithQuantity>>>
}