package com.waqasyounis.mvvm.shopping.list.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.waqasyounis.mvvm.shopping.list.db.dao.ShoppingItemDao
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import javax.inject.Inject

@Database(
    entities = [ShoppingItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val shoppingItemDao: ShoppingItemDao

    companion object {

        private val DATABASE_NAME = "shopping_database.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also {
                    INSTANCE = it
                }
            }

    }

}