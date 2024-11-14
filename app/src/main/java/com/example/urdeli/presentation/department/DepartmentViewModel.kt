package com.example.urdeli.presentation.department

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.urdeli.domain.model.DeliItem

class DepartmentViewModel : ViewModel() {
    val rowCount = 100
    var state by mutableStateOf(DepartmentState())

    init {
        val mutableDeliItems = state.deliItems.toMutableList()
        mutableDeliItems.addAll(List(10) {
           DeliItem(
               it,
               "Bread",
               "KG",
               10,
               10.0
           )
        })

        state = state.copy(
            deliItems = mutableDeliItems
        )
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
            state.deliItems.take(rowCount + 1).forEach {item -> _selectedRows.add(item.id)}

        updatedSelectedRow()
    }
    private fun updatedSelectedRow() {
        selectedRowId = if (_selectedRows.size == 1) _selectedRows.first() else null
    }

    private fun updatedHeaderSelectedState() {
        isHeaderSelected = _selectedRows.size == rowCount
    }

}