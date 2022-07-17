package com.waqasyounis.mvvm.shopping.list.util

import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

class DummyData {

    companion object {
        val shoppingItems: List<ShoppingItem>
            get() = listOf(
                ShoppingItem(name = "Apple", noOfItems = 1),
                ShoppingItem(name = "Mango", noOfItems = 2),
                ShoppingItem(name = "Pine Apple", noOfItems = 3),
                ShoppingItem(name = "Banana", noOfItems = 4),
                ShoppingItem(name = "Apricot", noOfItems = 5),
            )
    }
}