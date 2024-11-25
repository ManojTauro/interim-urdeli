package com.example.urdeli.data.mapper

import com.example.urdeli.data.local.entity.DeliItemEntity
import com.example.urdeli.domain.model.DeliItem

fun DeliItemEntity.toDeliItem(): DeliItem {
    return DeliItem(
        itemId = itemId,
        name = name,
        unit = unit,
        cost = cost,
    )
}