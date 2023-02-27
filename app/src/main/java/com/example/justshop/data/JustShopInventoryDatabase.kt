package com.example.justshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The Database class which provides the DAO to the app
 */
@Database(entities = [FavItem::class], version = 1, exportSchema = false)
abstract class JustShopInventoryDatabase : RoomDatabase() {

    abstract fun itemDao(): FavItemDao

    companion object {
        /**
         * Singleton instance of DB
         */
        @Volatile
        private var dbInstance: JustShopInventoryDatabase? = null

        fun getDatabase(context: Context): JustShopInventoryDatabase {
            return dbInstance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, JustShopInventoryDatabase::class.java, "fav_item_database"
                ).fallbackToDestructiveMigration().build().also { dbInstance = it }
            }
        }
    }
}