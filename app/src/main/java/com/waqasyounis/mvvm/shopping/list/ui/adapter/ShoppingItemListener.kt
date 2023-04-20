package com.waqasyounis.mvvm.shopping.list.ui.adapter

import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

interface ShoppingItemListener {
    fun updateShoppingItem(item: ShoppingItem)
    fun onDeleteClick(item: ShoppingItem)
}