package com.waqasyounis.mvvm.shopping.list.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

class ShoppingItemsDifferCallback(
    private val oldList: List<ShoppingItem>,
    private val newList: List<ShoppingItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList[newPosition].id
    }

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}