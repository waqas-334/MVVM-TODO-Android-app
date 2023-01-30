package com.waqasyounis.mvvm.shopping.list.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.waqasyounis.mvvm.shopping.list.R
import com.waqasyounis.mvvm.shopping.list.databinding.ActivityMainBinding
import com.waqasyounis.mvvm.shopping.list.db.AppDatabase
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepositoryImpl
import com.waqasyounis.mvvm.shopping.list.ui.adapter.ItemListener
import com.waqasyounis.mvvm.shopping.list.ui.adapter.ShoppingItemAdapter
import com.waqasyounis.mvvm.shopping.list.ui.adapter.SwipeToDeleteCallBack
import com.waqasyounis.mvvm.shopping.list.util.DummyData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        ShoppingItemAdapter(this@MainActivity)

    }
    private val addBottomSheet = AddItemBottomSheet.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initDatabase()
        initObservers()

    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is MainViewModel.TasksEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(
                            binding.root,
                            "${event.item.name} deleted",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("UNDO") {
                                viewModel.onUndoDelete(event.item)
                            }.show()
                    }
                }
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
                Toast.makeText(this@MainActivity, "Coming Soon", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_sort_by_quantity_desc -> {
                viewModel.sortSelected(SortOrder.QUANTITY_DESC)
                Toast.makeText(this@MainActivity, "Coming Soon", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initDatabase() {
        viewModel.getAllItems().observe(this) {
            adapter.differ.submitList(it)
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
                fabClicked()
            }

            fab.setOnLongClickListener {
                addDummyData()
                true
            }
        }


    }

    private fun addDummyData() = viewModel.addItems(DummyData.shoppingItems)

    private fun fabClicked() {
        addBottomSheet.show(supportFragmentManager) {
            viewModel.addItem(it)
        }
    }

    override fun onAddClicked(item: ShoppingItem) {
        viewModel.addItem(item.increase())
    }

    override fun onSubtractClicked(item: ShoppingItem) {
        viewModel.updateItem(item.decrease())
    }

    //    override fun onDeleteClicked(item: ShoppingItem) {
//        viewModel.deleteItem(item)
//    }
    override fun onDeleteClicked(item: ShoppingItem) {
        viewModel.deleteItem(item)
    }

}

enum class SortOrder {
    QUANTITY_ASC,
    QUANTITY_DESC,
}