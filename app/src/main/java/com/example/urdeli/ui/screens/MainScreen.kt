package com.example.urdeli.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urdeli.NavigationRailItem
import com.example.urdeli.ui.components.DataTable
import com.example.urdeli.ui.components.NavigationSideBar
import com.example.urdeli.ui.components.TableHeader
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val railItems = listOf(
        NavigationRailItem(
            title = "Departments",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationRailItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )

    // State for managing drawer visibility
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // List of primary destinations (in the rail)
    var selectedRailItem by remember { mutableIntStateOf(0) }
    var selectedSideDrawerItem by remember { mutableIntStateOf(0) }

    val sideDrawerItems = listOf(
        "Bakery Bread", "Bakery Cakes", "Deli Meats",
        "Hot Deli", "Deli Salads", "Sauces/Spices",
        "Ice Cream Bar", "Bakery Supplies", "Bakery"
    )

    // Modal Navigation Drawer that opens with secondary destinations

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFFF2F2F2)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = "Departments", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    sideDrawerItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item)
                            },
                            selected = selectedSideDrawerItem == index,
                            onClick = {
                                selectedSideDrawerItem = index
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = Color(0xFFF2F2F2),
            content = { _ ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavigationSideBar(
                        items = railItems,
                        selectedItem = selectedRailItem,
                        onNavigate = { selectedRailItem = it },
                        drawerState = drawerState,
                    )

                    // Main content based on selected rail item
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 24.dp),
                        shadowElevation = 2.dp,
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFBFBFB)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 32.dp, vertical = 20.dp)
                        ) {
                            Text(
                                text = "Store Name",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            TableHeader()
                            Spacer(modifier = Modifier.height(8.dp))
                            DataTable(sideDrawerItems[selectedRailItem])
                        }
                    }
                }
            }
        )
    }
}