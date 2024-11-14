package com.example.urdeli.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.urdeli.data.local.dao.UrdeliDao
import com.example.urdeli.data.local.entity.DeliItemEntity
import com.example.urdeli.data.local.entity.Department

@Database(
    entities = [Department::class, DeliItemEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun urdeliDao(): UrdeliDao

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
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}