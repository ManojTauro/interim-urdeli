package com.example.urdeli.domain.repository

import com.example.urdeli.domain.model.Store

interface StoreRepository {
    suspend fun insertStore(store: Store)
    suspend fun getAllStores(): List<Store>
}