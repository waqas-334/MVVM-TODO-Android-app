package com.waqasyounis.mvvm.shopping.list.db.repository

import androidx.room.Update
import com.waqasyounis.mvvm.shopping.list.db.dao.ShoppingItemDao
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import javax.inject.Inject

class ShoppingItemRepositoryImpl @Inject
constructor(
    private val dao: ShoppingItemDao
) {

    fun getAllItem() = dao.getAllItems()

    suspend fun updateItem(shoppingItem: ShoppingItem) = dao.upsert(shoppingItem)
    suspend fun insert(shoppingItem: ShoppingItem) = dao.upsert(shoppingItem)

    suspend fun delete(shoppingItem: ShoppingItem) = dao.delete(shoppingItem)
    suspend fun insertItems(shoppingItems: List<ShoppingItem>) = dao.insertItems(shoppingItems)
}