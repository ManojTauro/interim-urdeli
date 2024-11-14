package com.example.urdeli

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.lifecycleScope
import com.example.urdeli.data.local.AppDatabase
import com.example.urdeli.ui.screens.MainScreen
import com.example.urdeli.ui.theme.UrdeliTheme
import kotlinx.coroutines.launch

data class NavigationRailItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

class MainActivity : ComponentActivity() {
    private lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        appDatabase = AppDatabase.getInstance(this)

        val urdeliDao = appDatabase.urdeliDao()

        lifecycleScope.launch {
            Log.d("MainActivity", urdeliDao.getAllDeliItems().size.toString())
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UrdeliTheme {
                MainScreen()
            }
        }
    }
}




