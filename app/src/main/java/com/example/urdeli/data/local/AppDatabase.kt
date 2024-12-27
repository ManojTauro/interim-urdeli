package com.example.urdeli.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.urdeli.data.local.dao.DeliItemDao
import com.example.urdeli.data.local.dao.StocktakeEntryDao
import com.example.urdeli.data.local.dao.StoreDao
import com.example.urdeli.data.local.entity.DeliItemEntity
import com.example.urdeli.data.local.entity.DepartmentEntity
import com.example.urdeli.data.local.entity.StocktakeEntryEntity
import com.example.urdeli.data.local.entity.StoreEntity
import java.util.concurrent.Executors

@Database(
    entities = [
        DepartmentEntity::class,
        DeliItemEntity::class,
        StoreEntity::class,
        StocktakeEntryEntity::class
    ],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun urdeliDao(): DeliItemDao
    abstract fun stocktakeEntryDao(): StocktakeEntryDao
    abstract fun storeDao(): StoreDao

    companion object {
        private const val DATABASE_NAME = "urdeli_db"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/urdeli.db")
                    .setQueryCallback({ sqlQuery, bindArgs ->
                        Log.d("RoomQuery", "SQL: $sqlQuery | Args: ${bindArgs.joinToString()}")
                    }, Executors.newSingleThreadExecutor())
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}