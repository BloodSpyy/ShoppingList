package com.bloodspy.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun shoppingListDao(): ShopListDao

    companion object {
        private const val DB_NAME = "shopping_list.db"

        private val LOCK = Any()

        private var instance: AppDatabase? = null

        fun getInstance(application: Application): AppDatabase {
            instance?.let {
                return it
            }

            synchronized(LOCK) {
                instance?.let {
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                instance = db

                return db
            }
        }
    }
}