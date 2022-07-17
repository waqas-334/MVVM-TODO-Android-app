package com.waqasyounis.mvvm.shopping.list.db

import android.content.Context
import com.waqasyounis.mvvm.shopping.list.db.dao.ShoppingItemDao
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.nio.channels.Channel

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideShoppingItemDao(database: AppDatabase): ShoppingItemDao {
        return database.shoppingItemDao
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(context)
    }

    @Provides
    fun provideRepository(dao: ShoppingItemDao): ShoppingItemRepositoryImpl {
        return ShoppingItemRepositoryImpl(dao)
    }
}