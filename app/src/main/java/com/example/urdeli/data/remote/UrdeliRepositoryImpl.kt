package com.example.urdeli.data.remote

import android.content.Context
import com.example.urdeli.data.local.AppDatabase
import com.example.urdeli.data.mapper.toDeliItem
import com.example.urdeli.domain.model.DeliItem
import com.example.urdeli.domain.repository.UrdeliRepository
import com.example.urdeli.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UrdeliRepositoryImpl(
    context: Context,
) : UrdeliRepository {
    private val db = AppDatabase.getInstance(context)

    private val dao = db.urdeliDao()

    override suspend fun getDeliItems(
        departmentId: Int,
        query: String
    ): Flow<Resource<List<DeliItem>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val deliItems = dao.getDeliItems(departmentId, query)

            emit(
                Resource.Success(data = deliItems.map { it.toDeliItem() })
            )

            emit(Resource.Loading(isLoading = false))
        }
    }
}