package com.waqasyounis.mvvm.shopping.list.ui.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.databinding.ItemShoppingBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.Priority
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

class ShoppingItemViewHolder(private val binding: ItemShoppingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var listener: ShoppingItemListener? = null

    fun bind(item: ShoppingItem) {
        binding.apply {
            tvItems.text = item.noOfItems.toString()
            tvName.text = item.name
            setTaskPriority(item, this)

            ibDelete.setOnClickListener {
                listener?.onDeleteClick(item)
            }

            btnAdd.setOnClickListener {
                item.noOfItems = item.noOfItems + 1
                listener?.updateShoppingItem(item)
            }

            btnSubtract.setOnClickListener {
                if(item.noOfItems > 0) item.noOfItems = item.noOfItems - 1
                listener?.updateShoppingItem(item)
            }
        }
    }

    private fun setTaskPriority(item: ShoppingItem, binding: ItemShoppingBinding) {
        binding.tvPriority.text = item.priority.name
        // set the priority color based on the task priority
        val textColor = when (item.priority) {
            Priority.HIGH -> R.color.red
            Priority.MEDIUM -> R.color.yellow
            Priority.LOW -> R.color.green
        }
        binding.tvPriority.setTextColor(ContextCompat.getColor(itemView.context, textColor))
    }
}
