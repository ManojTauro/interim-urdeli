package com.example.urdeli.data.mapper

import com.example.urdeli.data.local.entity.StocktakeEntryEntity
import com.example.urdeli.domain.model.StocktakeEntry

fun StocktakeEntryEntity.toStocktakeEntry(): StocktakeEntry {
    return StocktakeEntry(
        id = id,
        itemId = itemId,
        quantity = quantity,
        stocktakeId = stocktakeId,
        departmentId = departmentId
    )
}

fun StocktakeEntry.toStocktakeEntryEntity(): StocktakeEntryEntity {
    return StocktakeEntryEntity(
        id = id,
        itemId = itemId,
        quantity = quantity,
        stocktakeId = stocktakeId,
        departmentId = departmentId
    )
}