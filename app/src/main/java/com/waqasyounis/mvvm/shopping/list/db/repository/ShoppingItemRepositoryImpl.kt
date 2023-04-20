package com.waqasyounis.mvvm.shopping.list.db.repository

import com.waqasyounis.mvvm.shopping.list.db.dao.ShoppingItemDao
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.ui.SortOrder
import javax.inject.Inject

class ShoppingItemRepositoryImpl @Inject
constructor(
    private val dao: ShoppingItemDao
) {
    fun getAllItem() = dao.getAllItems()

    fun updateItem(shoppingItem: ShoppingItem) = dao.upsert(shoppingItem)

    fun insert(shoppingItem: ShoppingItem) = dao.upsert(shoppingItem)

    fun delete(shoppingItem: ShoppingItem) = dao.delete(shoppingItem)

    fun insertItems(shoppingItems: List<ShoppingItem>) = dao.insertItems(shoppingItems)

    fun sort(sortOrder: SortOrder) = dao.sort(sortOrder)
}