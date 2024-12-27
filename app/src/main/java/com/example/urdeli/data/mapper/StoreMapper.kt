package com.example.urdeli.data.mapper

import com.example.urdeli.data.local.entity.StoreEntity
import com.example.urdeli.domain.model.Store

fun StoreEntity.toStore(): Store {
    return Store(
        stocktakeId = stocktakeId,
        storeName = storeName,
        storeLocation = storeLocation,
        supervisor = supervisor,
    )
}

fun Store.toStoreEntity(): StoreEntity {
    return StoreEntity(
        stocktakeId = this.stocktakeId,
        storeName = storeName,
        storeLocation = storeLocation,
        supervisor = supervisor,
    )
}