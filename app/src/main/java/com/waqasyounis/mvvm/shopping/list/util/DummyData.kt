package com.waqasyounis.mvvm.shopping.list.util

import com.waqasyounis.mvvm.shopping.list.db.entities.Priority
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

class DummyData {

    companion object {
        val shoppingItems: List<ShoppingItem>
            get() = listOf(
                ShoppingItem(name = "A", noOfItems = 1, Priority.LOW),
                ShoppingItem(name = "B", noOfItems = 2, Priority.MEDIUM),
                ShoppingItem(name = "C", noOfItems = 3, Priority.HIGH),
                ShoppingItem(name = "D", noOfItems = 4, Priority.MEDIUM),
                ShoppingItem(name = "E", noOfItems = 5, Priority.LOW),
            )
    }
}