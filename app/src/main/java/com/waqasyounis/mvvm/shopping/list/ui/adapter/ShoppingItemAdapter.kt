package com.waqasyounis.mvvm.shopping.list.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waqasyounis.mvvm.shopping.list.databinding.ItemShoppingBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import kotlin.math.log

private const val TAG = "ShoppingItemAdapter"

class ShoppingItemAdapter : RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder>() {

    var allItems: List<ShoppingItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ItemListener? = null

    inner class ViewHolder(private val binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingItem) {
            binding.apply {
                tvItems.text = item.noOfItems.toString()
                tvName.text = item.name

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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allItems[position])
    }

    override fun getItemCount() = allItems.size
}

interface ItemListener {
    fun onAddClicked(item: ShoppingItem)
    fun onSubtractClicked(item: ShoppingItem)
    fun onDeleteClicked(item: ShoppingItem)
}