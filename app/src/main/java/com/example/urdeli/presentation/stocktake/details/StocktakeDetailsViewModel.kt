package com.example.urdeli.presentation.stocktake.details

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.urdeli.StocktakeScreenRoute
import com.example.urdeli.data.remote.DeliItemRepositoryImpl
import com.example.urdeli.data.remote.StocktakeEntryRepositoryImpl
import com.example.urdeli.domain.model.StocktakeEntry
import com.example.urdeli.domain.repository.DeliItemRepository
import com.example.urdeli.domain.repository.StocktakeEntryRepository
import com.example.urdeli.util.Resource
import com.example.urdeli.util.roundToTwoDecimalPlaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class StocktakeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val rowCount = 100
    private val deliItemRepository: DeliItemRepository = DeliItemRepositoryImpl(context)
    private val stocktakeEntryRepository: StocktakeEntryRepository =
        StocktakeEntryRepositoryImpl(context)

    private val storeDetails = savedStateHandle.toRoute<StocktakeScreenRoute>()

    private val _state = MutableStateFlow(StocktakeStateDetails(isLoading = true))
    val state: StateFlow<StocktakeStateDetails> = _state

    val storeName: String = storeDetails.storeName
    private val _currentStockTakeId = MutableStateFlow(1)

    private val departments = mapOf(
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
        _state.value = _state.value.copy(
            departments = departments
        )
        Log.d("StocktakeViewModel", "stockTakeId: $storeDetails")
        getDeliItemsWithQuantity()
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
            _state.value.deliItems.take(rowCount + 1)
                .forEach { item -> _selectedRows.add(item.deliItem.itemId) }

        updatedSelectedRow()
    }

    fun updateQuantity(itemId: Int, newQuantity: String) {
        val quantity = newQuantity.toIntOrNull() ?: return

        if (quantity < 0) return

        Log.d("StocktakeDetailsViewModel", "selectedDepartment: ${_state.value.selectedDepartment}")

        viewModelScope.launch {
            stocktakeEntryRepository.updateQuantity(
                StocktakeEntry(
                    stocktakeId = _currentStockTakeId.value,
                    itemId = itemId,
                    quantity = quantity,
                    departmentId = _state.value.selectedDepartment
                )
            )
        }
    }

    fun onDepartmentSelected(departmentId: Int) {
        _state.value = _state.value.copy(selectedDepartment = departmentId)
        getDeliItemsWithQuantity()
    }

    fun updateStockTakeId(stockTakeId: Int) {
        _currentStockTakeId.value = stockTakeId
    }

    fun export() {
        viewModelScope.launch(Dispatchers.IO) {
            val outputFile: File = File.createTempFile("stocktake", ".xlsx")
            val items = stocktakeEntryRepository.getALlStockEntriesWithDeliItems(storeDetails.stocktakeId)
            val departmentMap = items.groupBy { it.deliItem.departmentId }

            Log.d("StocktakeDetailsViewModel", "departmentMap: $departmentMap")

            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Data")

            val storeNameRow = sheet.createRow(0)
            storeNameRow.createCell(0)
                .setCellValue("STORE NAME: ${storeDetails.storeName}")

            var rowIndex = 2

            val headerStyle = workbook.createCellStyle().apply {
                setFont(workbook.createFont().apply {
                    bold = true
                })
            }

            departmentMap.forEach { (departmentId, entries) ->
                // Write department name as a heading
                val departmentName = departments[departmentId] ?: "Unknown Department"

                // Write column headers
                val headerRow = sheet.createRow(rowIndex++)
                val headers = listOf("Department: $departmentName", "Cost", "Unit", "Quantity", "Total")
                headers.forEachIndexed { index, header ->
                    val cell = headerRow.createCell(index)
                    cell.setCellValue(header)
                    cell.cellStyle = headerStyle
                }

                // Write items under the department
                var departmentTotal = 0.0
                entries.forEach { entry ->
                    val dataRow = sheet.createRow(rowIndex++)
                    dataRow.createCell(0).setCellValue(entry.deliItem.name)
                    dataRow.createCell(1).setCellValue(entry.deliItem.cost)
                    dataRow.createCell(2).setCellValue(entry.deliItem.unit)
                    dataRow.createCell(3).setCellValue(entry.quantity.toDouble())
                    val itemTotal = entry.quantity * entry.deliItem.cost
                    departmentTotal += itemTotal
                    dataRow.createCell(4).setCellValue("EUR $itemTotal")
                }

                sheet.createRow(rowIndex++)
                val totalRow = sheet.createRow(rowIndex++)
                val cell1 = totalRow.createCell(0)
                cell1.cellStyle = headerStyle
                cell1.setCellValue("Department Total")

                val cell4 = totalRow.createCell(4)
                cell4.cellStyle = headerStyle
                cell4.setCellValue("EUR $departmentTotal")

                rowIndex++
            }

            // Write to the output file
            FileOutputStream(outputFile).use { fos ->
                workbook.write(fos)
            }
            workbook.close()
        }
    }

    private fun getDeliItemsWithQuantity(
        query: String = _state.value.searchQuery.lowercase()
    ) {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            val currentStockTakeId = _currentStockTakeId.value
            val selectedDepartment = _state.value.selectedDepartment

            deliItemRepository
                .getDeliItemsWithQuantity(currentStockTakeId, selectedDepartment, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val deliItems = result.data.orEmpty()
                            _state.value = _state.value.copy(
                                deliItems = deliItems,
                                departmentTotalMap = _state.value.departmentTotalMap.toMutableMap()
                                    .apply {
                                        this[selectedDepartment] =
                                            deliItems.sumOf { it.quantity * it.deliItem.cost }.roundToTwoDecimalPlaces()
                                    },
                                isLoading = false
                            )

                        }

                        is Resource.Error -> {
                            Unit
                        }

                        is Resource.Loading -> {}
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