package com.example.urdeli.presentation.stocktake.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urdeli.R

@Composable
fun StocktakeScreen(
    viewModel: StocktakeDetailsViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = viewModel.storeName,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(30)
                        ),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sync_saved_locally), // Replace with desired icon
                        contentDescription = "Save locally",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
                IconButton(
                    modifier = Modifier
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(30)
                        ),
                    onClick = { viewModel.export() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.file_export), // Replace with desired icon
                        contentDescription = "Export as CSV",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        TableHeader(
            onSelectedDepartment = { departmentId -> viewModel.onDepartmentSelected(departmentId) },
            departments = state.departments,
            departmentTotalMap = state.departmentTotalMap,
            selectedDepartment = state.selectedDepartment,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DataTable(
            deliItems = state.deliItems,
            isRowSelected = { rowId -> viewModel.selectedRows.contains(rowId) },
            onRowSelected = { itemId, isSelected -> viewModel.onRowSelected(itemId, isSelected) },
            onHeaderSelected = { isSelected -> viewModel.onHeaderSelected(isSelected) },
            isHeaderSelected = viewModel.isHeaderSelected,
            updateQuantity = { itemId, newQuantity ->
                viewModel.updateQuantity(
                    itemId,
                    newQuantity
                )
            }
        )
    }
}