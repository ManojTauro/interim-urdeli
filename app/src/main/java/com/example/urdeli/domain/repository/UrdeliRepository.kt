package com.example.urdeli.domain.repository

import com.example.urdeli.domain.model.DeliItem
import com.example.urdeli.util.Resource
import kotlinx.coroutines.flow.Flow

interface UrdeliRepository {
    suspend fun getDeliItems(
        departmentId: Int,
        query: String
    ) : Flow<Resource<List<DeliItem>>>
}