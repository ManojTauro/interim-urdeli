package com.example.urdeli.data.remote

import android.content.Context
import com.example.urdeli.data.local.AppDatabase
import com.example.urdeli.data.mapper.toStore
import com.example.urdeli.data.mapper.toStoreEntity
import com.example.urdeli.domain.model.Store
import com.example.urdeli.domain.repository.StoreRepository

class StoreRepositoryImpl(
    context: Context
): StoreRepository {
    private val db = AppDatabase.getInstance(context)

    private val dao = db.storeDao()

    override suspend fun insertStore(store: Store) {
        dao.insertStore(store.toStoreEntity())
    }

    override suspend fun getAllStores(): List<Store> {
        return dao.getStores().map {
            store -> store.toStore()
        }
    }
}