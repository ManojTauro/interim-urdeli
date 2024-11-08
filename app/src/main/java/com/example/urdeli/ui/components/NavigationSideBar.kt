package com.example.urdeli.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.urdeli.NavigationRailItem
import kotlinx.coroutines.launch

@Composable
fun NavigationSideBar(
    items: List<NavigationRailItem>,
    selectedItem: Int,
    onNavigate: (Int) -> Unit,
    drawerState: DrawerState,
) {
    val scope = rememberCoroutineScope()
    NavigationRail(
        containerColor = Color(0xFFF2F2F2),
        header = {
            IconButton(onClick = {
                scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
        ) {
            items.forEachIndexed { index, item ->
                NavigationRailItem(
                    selected = selectedItem == index,
                    onClick = { onNavigate(index) },
                    icon = {
                        Icon(
                            imageVector = if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }
}