package com.example.urdeli.presentation.department

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.urdeli.data.remote.UrdeliRepositoryImpl
import com.example.urdeli.domain.repository.UrdeliRepository
import com.example.urdeli.util.Resource
import kotlinx.coroutines.launch

class DepartmentViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val rowCount = 100
    private val repository: UrdeliRepository = UrdeliRepositoryImpl(context)
    var state by mutableStateOf(DepartmentState())

    val departments = mapOf(
        1 to "Bakery Bread",
        2 to "Bakery Cakes",
        3 to "Deli Meats",
        4 to "Hot Deli",
        5 to "Deli Salads",
        6 to "Sauces/Spices",
        7 to "Ice Cream Bar",
        8 to "Bakery Supplies",
        9 to "Bakery"
    )

    init {
        state = state.copy(
            departments = departments
        )
        getDeliItems()
    }

    private val _selectedRows = mutableStateListOf<Int>()
    val selectedRows: List<Int> get() = _selectedRows

    var isHeaderSelected by mutableStateOf(false)
        private set

    var selectedRowId: Int? by mutableStateOf(null)

    fun onRowSelected(id: Int, isSelected: Boolean) {
        if (isSelected) _selectedRows.add(id)
        else _selectedRows.remove(id)

        updatedSelectedRow()
        updatedHeaderSelectedState()
    }

    fun onHeaderSelected(isChecked: Boolean) {
        isHeaderSelected = isChecked
        _selectedRows.clear()

        if (isChecked)
            state.deliItems.take(rowCount + 1).forEach { item -> _selectedRows.add(item.itemId) }

        updatedSelectedRow()
    }

    fun updateQuantity(itemId: Int, newQuantity: String) {
        val quantity = newQuantity.toIntOrNull() ?: return

        if (quantity < 0) return

        state = state.copy(
            deliItemsQuantity = state.deliItemsQuantity.toMutableMap().apply {
                this[itemId] = (this[itemId] ?: 0) + quantity
            }
        )
    }

    fun onDepartmentSelected(departmentId: Int) {
        state = state.copy(departmentId = departmentId)
        getDeliItems(departmentId)
    }

    private fun getDeliItems(
        departmentId: Int = 1,
        query: String = state.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            repository
                .getDeliItems(departmentId, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { items ->
                                state = state.copy(
                                    deliItems = items,
                                )
                            }
                        }

                        is Resource.Error -> { Unit }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }

        }
    }

    private fun updatedSelectedRow() {
        selectedRowId = if (_selectedRows.size == 1) _selectedRows.first() else null
    }

    private fun updatedHeaderSelectedState() {
        isHeaderSelected = _selectedRows.size == rowCount
    }
}