package com.example.urdeli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urdeli.data.local.AppDatabase
import com.example.urdeli.presentation.components.NavigationSideBar
import com.example.urdeli.presentation.stocktake.listing.StocktakeListingScreen
import com.example.urdeli.presentation.stocktake.details.StocktakeScreen
import com.example.urdeli.ui.theme.UrdeliTheme
import kotlinx.serialization.Serializable

data class NavigationRailItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

class MainActivity : ComponentActivity() {
    private lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        val railItems = listOf(
            NavigationRailItem(
                title = "Stocktakes",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
            ),
//            NavigationRailItem(
//                title = "Settings",
//                selectedIcon = Icons.Filled.Settings,
//                unselectedIcon = Icons.Outlined.Settings,
//            ),
        )

//    var selectedSideDrawerItem by remember { mutableIntStateOf(0) }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UrdeliTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                var selectedRailItem by remember { mutableIntStateOf(0) }
                val navController = rememberNavController()

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
                                onNavigate = {
                                    selectedRailItem = it
                                    navController.navigate(StocktakeListingScreenRoute)
                                },
                                drawerState = drawerState,
                            )

                            // Main content based on selected rail item
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp, vertical = 24.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFFBFBFB)
                            ) {
                                NavHost(
                                    navController = navController,
                                    startDestination = StocktakeListingScreenRoute
                                ) {
                                    composable<StocktakeListingScreenRoute> {
                                        StocktakeListingScreen(
                                            onNewClicked = { },
                                            onEditClicked = { },
                                            onGotoStockTakeScreen = { stocktakeId, storeName ->
                                                navController.navigate(
                                                    StocktakeScreenRoute(stocktakeId, storeName)
                                                )
                                            },
                                        )
                                    }
                                    composable<StocktakeScreenRoute> { backStackEntry ->
                                        StocktakeScreen()
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Serializable
object StocktakeListingScreenRoute

@Serializable
data class StocktakeScreenRoute(
    val stocktakeId: Int,
    val storeName: String = "",
)


