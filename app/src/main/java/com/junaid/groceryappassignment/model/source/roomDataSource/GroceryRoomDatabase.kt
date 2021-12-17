package com.junaid.groceryappassignment.model.source.roomDataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.model.data.TimestampConverter
import com.junaid.groceryappassignment.utils.general.AppConstants.DATABASE_NAME


@Database(entities = [GroceryListItem::class,GroceryItem::class], version = 1, exportSchema = false)
@TypeConverters(TimestampConverter::class)
abstract class GroceryRoomDatabase : RoomDatabase() {
    abstract fun groceryListDao(): GroceryListDao
    abstract fun groceryItemDao(): GroceryItemDao

    companion object {
        @Volatile
        private var INSTANCE: GroceryRoomDatabase? = null
        fun getDatabase(context: Context): GroceryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}