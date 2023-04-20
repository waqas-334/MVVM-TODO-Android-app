package com.waqasyounis.mvvm.shopping.list.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.databinding.BottomsheetAddItemBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.Priority
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

class AddItemBottomSheetDialogFragment : BottomSheetDialogFragment() {

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
                    noOfItems = quantity,
                    priority = getCheckPriority()
                )

                setFragmentResult(ADD_ITEM_REQUEST_KEY, bundleOf(SHOPPING_ITEM_BUNDLE_KEY to item))

                tietName.text?.clear()
                tietQuantity.text?.clear()

                if (isVisible) dismiss()
            }
        }
    }

    private fun getCheckPriority(): Priority {
        return when (binding.rgPriority.checkedRadioButtonId) {
            R.id.rb_high -> Priority.HIGH
            R.id.rb_medium -> Priority.MEDIUM
            else -> Priority.LOW
        }
    }

    companion object {

        val FRAGMENT_NAME = AddItemBottomSheetDialogFragment::class.java.simpleName
        const val ADD_ITEM_REQUEST_KEY = "AddItemRequestKey"
        const val SHOPPING_ITEM_BUNDLE_KEY = "ShoppingItemBundleKey"

        val INSTANCE: AddItemBottomSheetDialogFragment by lazy { AddItemBottomSheetDialogFragment() }
    }
}