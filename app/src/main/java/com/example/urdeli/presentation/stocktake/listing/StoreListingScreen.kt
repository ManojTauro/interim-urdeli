package com.example.urdeli.presentation.stocktake.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urdeli.domain.model.Store

@Composable
fun StocktakeListingScreen(
    viewModel: StoreListingViewModel = viewModel(),
    onNewClicked: () -> Unit,
    onEditClicked: (Store) -> Unit,
    onGotoStockTakeScreen: (stocktakeId: Int, storeName: String) -> Unit
) {
    val (showModal, setShowModal) = remember { mutableStateOf(false) }
    val stores = viewModel.stores.collectAsState(initial = emptyList()).value

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
        ) {
            items(stores) { stocktakeItem ->
                StocktakeCard(
                    store = stocktakeItem,
                    onEditClicked = onEditClicked,
                    onGotoStockTakeScreen = onGotoStockTakeScreen
                )
            }
        }

        if (stores.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "No Stocktakes found.")
            }
        }



        if (showModal) {
            StocktakeModal(
                onDismiss = { setShowModal(false) },
                onSave = { storeName, storeLocation, supervisor ->
                    viewModel.addStore(storeName, storeLocation, supervisor)
                    setShowModal(false)
                }
            )
        }
    }
}

@Composable
fun StocktakeCard(
    store: Store,
    onEditClicked: (Store) -> Unit,
    onGotoStockTakeScreen: (stocktakeId: Int, storeName: String) -> Unit
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
                store.storeName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF333333),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                "Total: ${0.0} EUR",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF333333),
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onEditClicked(store) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        contentColor = Color(0xFF222222)
                    )
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = { onGotoStockTakeScreen(store.stocktakeId!!, store.storeName) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Go to Stocktake")
                }
            }
        }
    }
}

@Composable
fun StocktakeModal(
    onDismiss: () -> Unit,
    onSave: (
        storeName: String,
        storeLocation: String,
        supervisor: String
    ) -> Unit
) {
    var storeName by remember { mutableStateOf("") }
    var storeLocation by remember { mutableStateOf("") }
    var supervisor by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Stocktake") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Store Name") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = storeLocation,
                    onValueChange = { storeLocation = it },
                    label = { Text("Store Location") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = supervisor,
                    onValueChange = { supervisor = it },
                    label = { Text("Supervisor") }
                )
            }
        },
        confirmButton = {
            Button(

                onClick = {
                    onSave(storeName, storeLocation, supervisor)
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

data class StoreData(
    val stocktakeId: Int,
    val storeName: String,
    val storeLocation: String,
    val supervisor: String,
    val balance: Double? = 0.0,
    val currency: String? = "EUR"
)