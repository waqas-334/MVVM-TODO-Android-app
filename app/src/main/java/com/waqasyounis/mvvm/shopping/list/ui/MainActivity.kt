package com.waqasyounis.mvvm.shopping.list.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.waqasyounis.mvvm.shopping.list.databinding.ActivityMainBinding
import com.waqasyounis.mvvm.shopping.list.db.AppDatabase
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepository
import com.waqasyounis.mvvm.shopping.list.ui.adapter.ItemListener
import com.waqasyounis.mvvm.shopping.list.ui.adapter.ShoppingItemAdapter
import com.waqasyounis.mvvm.shopping.list.util.DummyData

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = ShoppingItemAdapter()

    private val addBottomSheet = AddItemBottomSheet.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase(this)
        val repository = ShoppingItemRepository(database.shoppingItemDao)
        val factory = ShoppingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        initViews()
        initDatabase()
    }

    private fun initDatabase() {
        viewModel.getAllItems().observe(this) {
            Log.d(TAG, "initDatabase: $it")
            adapter.allItems = it
        }
    }

    private fun initViews() {
        binding.apply {
            rv.adapter = this@MainActivity.adapter
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            fab.setOnClickListener {
                fabClicked()
            }

            fab.setOnLongClickListener {
                addDummyData()
                true
            }
        }
        adapter.listener = this

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

    override fun onDeleteClicked(item: ShoppingItem) {
        viewModel.deleteItem(item)
    }

}