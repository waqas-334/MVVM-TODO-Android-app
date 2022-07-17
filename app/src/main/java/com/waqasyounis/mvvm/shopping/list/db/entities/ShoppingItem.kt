package com.waqasyounis.mvvm.shopping.list.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    val name: String,
    val noOfItems: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    fun increase(): ShoppingItem {
        val addedItem = this.copy(noOfItems = this.noOfItems + 1)
        addedItem.id = this.id
        return addedItem
    }

    fun decrease(): ShoppingItem {
        if (this.noOfItems == 0) return this
        val addedItem = this.copy(noOfItems = this.noOfItems - 1)
        addedItem.id = this.id
        return addedItem
    }

}
