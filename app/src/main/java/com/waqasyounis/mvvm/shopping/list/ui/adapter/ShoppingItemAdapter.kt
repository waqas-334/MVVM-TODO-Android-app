package com.waqasyounis.mvvm.shopping.list.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.waqasyounis.mvvm.shopping.list.databinding.ItemShoppingBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

class ShoppingItemAdapter(private val listener: ShoppingItemListener) :
    RecyclerView.Adapter<ShoppingItemViewHolder>() {

    private val shoppingList = mutableListOf<ShoppingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding =
            ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ShoppingItemViewHolder(binding)
        viewHolder.listener = listener
        return viewHolder
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        holder.bind(shoppingList[position])
    }

    override fun getItemCount() = shoppingList.size

    fun itemRemoved(position: Int) {
        listener.onDeleteClick(shoppingList[position])
    }

    fun setItems(items: List<ShoppingItem>) {
        val diffCallback = ShoppingItemsDifferCallback(shoppingList, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        shoppingList.clear()
        shoppingList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
}