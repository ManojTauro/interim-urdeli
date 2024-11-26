package com.example.urdeli.presentation.stocktake.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableHeader(
    onSelectedDepartment: (departmentId: Int) -> Unit,
    departments: Map<Int, String>,
    departmentTotalMap: Map<Int, Double>,
    selectedDepartment: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DepartmentSelector(
            currentDepartment = departments[selectedDepartment] ?: "Bakery Bread",
            onDepartmentSelected = { department ->
                onSelectedDepartment(department)
            },
            departments = departments,
            departmentTotal = departmentTotalMap[selectedDepartment] ?: 0.0
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchBar(
                text = "Search",
                onTextChange = {}
            )
            IconButton(
                modifier = Modifier
                    .background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(30)),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete, // Replace with desired icon
                    contentDescription = "Delete Row",
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = { /* Handle button click */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .height(44.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        tint = Color.White
                    )

                    Text(
                        text = "Add item",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String = "Search"
) {
    Box(
        modifier = Modifier
            .border(
                1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(25),
            )
            .height(40.dp)
            .padding(horizontal = 8.dp)
            .width(250.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            decorationBox = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 4.dp) // Padding for spacing
                    )
                    Text(
                        text = placeholderText,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        )
    }
}

@Composable
fun DepartmentSelector(
    currentDepartment: String,
    onDepartmentSelected: (Int) -> Unit,
    departments: Map<Int, String>,
    departmentTotal: Double
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .width(175.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(25)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentDepartment,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Icon(
                    imageVector = if (expanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Select department",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.Black)
                    .width(175.dp)
            ) {
                departments.forEach { (id, name) ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = name,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        },
                        onClick = {
                            onDepartmentSelected(id)
                            expanded = false
                        },
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxWidth()
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(start = 8.dp, top = 6.dp),
            text = "€ $departmentTotal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}