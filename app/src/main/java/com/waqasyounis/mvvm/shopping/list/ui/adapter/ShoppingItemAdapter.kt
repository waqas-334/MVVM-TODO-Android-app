package com.waqasyounis.mvvm.shopping.list.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

private const val TAG = "ShoppingItemAdapter"

class ShoppingItemAdapter(private val listener: ItemListener) : RecyclerView.Adapter<ShoppingItemViewHolder>() {

//    var allItems: List<ShoppingItem> = listOf()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val viewHolder = ShoppingItemViewHolder.create(parent)
        viewHolder.listener = listener
        return viewHolder
    }


    private val differCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id== newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem;
//            return oldItem.noOfItems == newItem.noOfItems && oldItem.name == newItem.name && oldItem.priority==newItem.priority
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    fun itemRemoved(position: Int) {
//        notifyItemRemoved(position)
        listener.onDeleteClicked(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}

class SwipeToDeleteCallBack(private val adapter: ShoppingItemAdapter, context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT )  {
    //https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e

    private val icon = ContextCompat.getDrawable(context,R.drawable.ic_delete_fill0_wght400_grad0_opsz48)
    private val background = ColorDrawable(Color.RED)


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.itemRemoved(viewHolder.adapterPosition)

    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20 //so background is behind the rounded corners of itemView


        val iconMargin: Int = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop: Int = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

//        if (dX > 0) { // Swiping to the right
//            val iconLeft: Int = itemView.left + iconMargin + icon.intrinsicWidth
//            val iconRight: Int = itemView.left + iconMargin
//            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//            background.setBounds(
//                itemView.left, itemView.top,
//                itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
//            )
//        } else
            if (dX < 0) { // Swiping to the left
            val iconLeft: Int = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight: Int = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)

    }

}


interface ItemListener {
    fun onAddClicked(item: ShoppingItem)
    fun onSubtractClicked(item: ShoppingItem)
    fun onDeleteClicked(item: ShoppingItem)
}