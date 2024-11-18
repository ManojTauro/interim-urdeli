package com.example.urdeli.presentation.department

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState

@Composable
fun DataTable(
    viewModel: DepartmentViewModel = viewModel(),
) {
    val state = viewModel.state
    var selectedItemId by remember { mutableStateOf<Int?>(null) }
    var isDropdownVisible by remember { mutableStateOf(false) }

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

    TableHeader(
        onSelectedDepartment = { departmentId -> viewModel.onDepartmentSelected(departmentId) },
        departments = state.departments
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
        state.deliItems.forEach { item ->
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
                    Text(item.unit)
                }
                cell {
                    Text("${item.cost}")
                }
                cell {
                    Box {
                        Text(
                            text = state.deliItemsQuantity[item.itemId]?.toString()
                                ?: "Enter Quantity",
                            modifier = Modifier
                                .clickable {
                                    selectedItemId = item.itemId
                                    isDropdownVisible = true
                                }
                                .padding(8.dp),
                            color = if (state.deliItemsQuantity[item.itemId] != null) Color.Black else Color.Gray
                        )

                        QuantityInputDropdown(
                            expanded = isDropdownVisible && selectedItemId == item.itemId,
                            onDismiss = {
                                isDropdownVisible = false
                                selectedItemId = null
                            },
                            onSave = { newQuantity ->
                                viewModel.updateQuantity(
                                    item.itemId,
                                    newQuantity
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuantityInputDropdown(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var quantityInput by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(expanded) {
        if (expanded) {
            focusRequester.requestFocus()
        }
    }

    AnimatedVisibility(
        visible = expanded
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .width(250.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Quantity",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .height(40.dp)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = quantityInput,
                        onValueChange = { quantityInput = it },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                            quantityInput = ""
                        }
                    ) {
                        Text(
                            "Cancel",
                            color = Color.White
                        )
                    }
                    TextButton(
                        onClick = {
                            onSave(quantityInput)
                            onDismiss()
                            quantityInput = ""
                        }
                    ) {
                        Text(
                            "Save",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}