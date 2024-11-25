package com.example.urdeli.data.remote

import android.content.Context
import com.example.urdeli.data.local.AppDatabase
import com.example.urdeli.domain.repository.DeliItemRepository
import com.example.urdeli.shared.DeliItemWithQuantity
import com.example.urdeli.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

class DeliItemRepositoryImpl(
    context: Context,
) : DeliItemRepository {
    private val db = AppDatabase.getInstance(context)

    private val dao = db.urdeliDao()

    override suspend fun getDeliItemsWithQuantity(
        stockTakeId: Int,
        departmentId: Int,
        query: String
    ): Flow<Resource<List<DeliItemWithQuantity>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            dao.getDeliItemsWithQuantity(stockTakeId, departmentId, query)
                .distinctUntilChanged()
                .collect { deliItems ->
                    emit(Resource.Success(data = deliItems))
                }

            emit(Resource.Loading(isLoading = false))
        }
    }
}