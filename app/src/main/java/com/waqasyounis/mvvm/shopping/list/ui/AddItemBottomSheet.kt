package com.waqasyounis.mvvm.shopping.list.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.databinding.BottomsheetAddItemBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import java.lang.NumberFormatException

typealias OnOkayClickListener = (item: ShoppingItem) -> Unit

class AddItemBottomSheet : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "AddItemBottomSheet"
        val INSTANCE: AddItemBottomSheet by lazy { AddItemBottomSheet() }
    }

    var onOkayClickListener: OnOkayClickListener? = null
    private lateinit var item: ShoppingItem

    private lateinit var binding: BottomsheetAddItemBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
    }

    private fun initActions() {
        binding.apply {

            btnAdd.setOnClickListener {

                val name = tietName.text.toString()
                if (TextUtils.isEmpty(name)) {
                    fieldName.error = getString(R.string.invalid_value)
                    return@setOnClickListener
                }

                val quantity: Int = try {
                    tietQuantity.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    fieldQuantity.error = getString(R.string.invalid_value)
                    e.printStackTrace()
                    return@setOnClickListener
                }




                item = ShoppingItem(
                    name = name,
                    noOfItems = quantity
                )
                onOkayClickListener?.let {
                    it(item)
                }

                if (isVisible) dismiss()
            }

        }
    }

    fun show(fragmentManager: FragmentManager, onOkayClickListener: OnOkayClickListener) {
        this.onOkayClickListener = onOkayClickListener
        super.show(fragmentManager, TAG)
    }

}