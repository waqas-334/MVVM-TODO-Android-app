package com.waqasyounis.mvvm.shopping.list.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.databinding.ItemShoppingBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.Priority
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

private const val TAG = "ShoppingItemViewHolder"
class ShoppingItemViewHolder(private val binding: ItemShoppingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var listener: ItemListener? = null
    fun bind(item: ShoppingItem) {
        binding.apply {
            tvItems.text = item.noOfItems.toString()
            tvName.text = item.name
            setTaskPriority(item, this)

            ibDelete.setOnClickListener {
                listener?.onDeleteClicked(item)
            }
            btnAdd.setOnClickListener {
                Log.i(TAG, "bind: addClicked")
                listener?.onAddClicked(item)
            }

            btnSubtract.setOnClickListener {
                Log.i(TAG, "bind: btnSubtract")
                listener?.onSubtractClicked(item)
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

    companion object {
        fun create(parent: ViewGroup): ShoppingItemViewHolder {
            val binding =
                ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShoppingItemViewHolder(binding)

        }
    }


}
