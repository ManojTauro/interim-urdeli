package com.example.urdeli.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun StocktakeScreen(
//    stocktakeData: List<StocktakeData> = emptyList(),
    onNewClicked: () -> Unit,
    onEditClicked: (StocktakeData) -> Unit,
    onGotoNextScreen: (StocktakeData) -> Unit
) {
    val (showModal, setShowModal) = remember { mutableStateOf(false) }

    val stocktakeData = listOf(
        StocktakeData(
            storeName = "Store 1"
        ),
        StocktakeData(
            storeName = "Store 2"
        ),
        StocktakeData(
            storeName = "Store 3"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Stock takes",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = { setShowModal(true) },
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
                        text = "Stocktake",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        HorizontalDivider()

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            columns = GridCells.FixedSize(500.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(stocktakeData) { stocktakeItem ->
                StocktakeCard(
                    stocktakeData = stocktakeItem,
                    onEditClicked = onEditClicked,
                    onGotoNextScreen = onGotoNextScreen
                )
            }
        }

        if (showModal) {
            StocktakeModal(
                onDismiss = { setShowModal(false) },
                onSave = { newStocktakeData ->
//                    onSaveNewStocktake(newStocktakeData)
                    setShowModal(false)
                }
            )
        }
    }
}

@Composable
fun StocktakeCard(
    stocktakeData: StocktakeData,
    onEditClicked: (StocktakeData) -> Unit,
    onGotoNextScreen: (StocktakeData) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                stocktakeData.storeName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF333333),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                "Total: ${stocktakeData.balance} ${stocktakeData.currency}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF333333),
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onEditClicked(stocktakeData) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        contentColor = Color(0xFF222222)
                    )
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = { onGotoNextScreen(stocktakeData) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Go to next")
                }
            }
        }
    }
}

@Composable
fun StocktakeModal(
    onDismiss: () -> Unit,
    onSave: (StocktakeData) -> Unit
) {
    var storeName by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf(0.0) }
    var currency by remember { mutableStateOf("USD") }
    var expirationDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Card") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Store Name") }
                )
                TextField(
                    value = balance.toString(),
                    onValueChange = { balance = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Balance") }
                )
                TextField(
                    value = currency,
                    onValueChange = { currency = it },
                    label = { Text("Currency") }
                )
                TextField(
                    value = expirationDate,
                    onValueChange = { expirationDate = it },
                    label = { Text("Expiration Date") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
//                    onSave(
//                        CardData(
//                            storeName = storeName,
//                            balance = balance,
//                            currency = currency,
//                            expirationDate = expirationDate
//                        )
//                    )
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

data class StocktakeData(
    val storeName: String,
    val balance: Double? = 0.0,
    val currency: String? = "USD"
)