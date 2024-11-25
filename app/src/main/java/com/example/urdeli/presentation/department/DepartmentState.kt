package com.example.urdeli.presentation.department

import com.example.urdeli.shared.DeliItemWithQuantity

data class DepartmentState(
    val deliItems: List<DeliItemWithQuantity> = emptyList(),
    val departments: Map<Int, String> = emptyMap(),
    val departmentTotalMap: Map<Int, Double> = emptyMap(),
    val selectedDepartment: Int = 1,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
)
