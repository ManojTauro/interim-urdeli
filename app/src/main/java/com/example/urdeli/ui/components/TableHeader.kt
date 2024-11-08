package com.example.urdeli.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Bakery Bread",
            modifier = Modifier.weight(1f),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(35.dp),
                placeholder = { Text("Search") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = RoundedCornerShape(30) // Rounded corners
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Favorite, // Replace with desired icon
                    contentDescription = "Icon 1"
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Share, // Replace with desired icon
                    contentDescription = "Icon 2"
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.MoreVert, // Replace with desired icon
                    contentDescription = "Icon 3"
                )
            }
        }
    }
}