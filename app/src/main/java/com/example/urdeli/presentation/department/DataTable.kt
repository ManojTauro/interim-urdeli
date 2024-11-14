package com.example.urdeli.presentation.department

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState

@Composable
fun DataTable(
    department: String,
    viewModel: DepartmentViewModel = viewModel(),
) {
    val columns = listOf(
        DataColumn(
            width = TableColumnWidth.Fixed(60.dp),
        ) {
            Checkbox(
                modifier = Modifier.padding(8.dp),
                checked = viewModel.isHeaderSelected,
                onCheckedChange = { viewModel.onHeaderSelected(it) },
            )
        },
        DataColumn(
            width = TableColumnWidth.Fixed(400.dp),
        ) {
            Text(
                text = "Name",
            )
        },
        DataColumn(
            width = TableColumnWidth.Fixed(200.dp),
        ) {
            Text(
                text = "Unit",
            )
        },
        DataColumn(
            width = TableColumnWidth.Fixed(200.dp),
        ) {
            Text(
                text = "Cost",
            )
        },
        DataColumn(
            width = TableColumnWidth.Fixed(200.dp),
        ) {
            Text(
                text = "Quantity",
            )
        },
    )

    PaginatedDataTable(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBFBFB)),
        columns = columns,
        rowHeight = 45.dp,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
//        rowBackgroundColor = { Color(0xFFF2F2F2) }, // Selected row background color
        rowBackgroundColor = { Color(0xFFFBFBFB) },
        headerBackgroundColor = Color(0xFFFBFBFB),
        footerBackgroundColor = Color(0xFFFBFBFB),
        state = rememberPaginatedDataTableState(10),
    ) {
        viewModel.state.deliItems.forEach { item ->
            val isRowSelected = viewModel.selectedRows.contains(item.itemId)

            row {
                cell {
                    Checkbox(
                        modifier = Modifier.padding(8.dp),
                        checked = isRowSelected,
                        onCheckedChange = {
                            viewModel.onRowSelected(item.itemId, it)
                        }
                    )
                }
                cell {
                    Text(item.name)
                }
                cell {
                    item.unit?.let { Text(it) }
                }
                cell {
                    Text("${item.cost}")
                }
                cell {
//                    Text("${item.quantity}")
                }

            }
        }
    }
}