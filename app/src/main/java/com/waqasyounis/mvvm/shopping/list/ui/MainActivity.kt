package com.waqasyounis.mvvm.shopping.list.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.databinding.ActivityMainBinding
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.ui.AddItemBottomSheetDialogFragment.Companion.ADD_ITEM_REQUEST_KEY
import com.waqasyounis.mvvm.shopping.list.ui.AddItemBottomSheetDialogFragment.Companion.SHOPPING_ITEM_BUNDLE_KEY
import com.waqasyounis.mvvm.shopping.list.ui.adapter.ShoppingItemAdapter
import com.waqasyounis.mvvm.shopping.list.ui.adapter.ShoppingItemListener
import com.waqasyounis.mvvm.shopping.list.ui.adapter.SwipeToDeleteCallBack
import com.waqasyounis.mvvm.shopping.list.util.DummyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ShoppingItemListener {

    private val viewModel: MainViewModel by viewModels()
    private val addItemBottomSheetDialogFragment = AddItemBottomSheetDialogFragment.INSTANCE
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        ShoppingItemAdapter(this@MainActivity)
    }

    private fun addDummyData() = viewModel.addItems(DummyData.shoppingItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initDatabase()
        initObservers()
        onResultListeners()
    }

    private fun initDatabase() {
        viewModel.itemUiModel.observe(this) {
            adapter.setItems(it.listOfItems)
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is MainViewModel.TasksEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(
                            binding.root, "${event.item.name} deleted", Snackbar.LENGTH_LONG
                        ).setAction("UNDO") {
                            viewModel.onUndoDelete(event.item)
                        }.show()
                    }
                }
            }
        }
    }

    private fun onResultListeners() {
        supportFragmentManager
            .setFragmentResultListener(ADD_ITEM_REQUEST_KEY, this) { requestKey, bundle ->
                bundle.getParcelable<ShoppingItem>(SHOPPING_ITEM_BUNDLE_KEY)?.let { shoppingItem ->
                    viewModel.addItem(shoppingItem)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_quantity_asc -> {
                viewModel.sortSelected(SortOrder.QUANTITY_ASC)
                true
            }
            R.id.action_sort_by_quantity_desc -> {
                viewModel.sortSelected(SortOrder.QUANTITY_DESC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallBack(adapter, this))
        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.apply {
            rv.adapter = this@MainActivity.adapter
            val decoration =
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            rv.addItemDecoration(decoration)

            fab.setOnClickListener {
                onFabClick()
            }

            fab.setOnLongClickListener {
                addDummyData()
                true
            }
        }
    }

    private fun onFabClick() {
        addItemBottomSheetDialogFragment.show(
            supportFragmentManager,
            AddItemBottomSheetDialogFragment.FRAGMENT_NAME
        )
    }

    override fun updateShoppingItem(item: ShoppingItem) {
        viewModel.updateItem(item)
    }

    override fun onDeleteClick(item: ShoppingItem) {
        viewModel.deleteItem(item)
    }
}

enum class SortOrder {
    QUANTITY_ASC, QUANTITY_DESC, NONE,
}