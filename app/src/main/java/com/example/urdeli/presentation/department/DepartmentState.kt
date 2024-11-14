package com.example.urdeli.presentation.department

import com.example.urdeli.domain.model.DeliItem

data class DepartmentState(
    val deliItems: List<DeliItem> = emptyList(),
    val departmentId: Int = 0,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
)
