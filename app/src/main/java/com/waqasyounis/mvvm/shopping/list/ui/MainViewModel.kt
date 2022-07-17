package com.waqasyounis.mvvm.shopping.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ShoppingItemRepository) : ViewModel() {

    fun getAllItems() = repository.getAllItem()
    fun addItems(shoppingItems: List<ShoppingItem>) {
        viewModelScope.launch(IO) {
            repository.insertItems(shoppingItems)
        }
    }

    fun updateItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch(IO) {
            repository.updateItem(shoppingItem)
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch(IO) {
            repository.delete(item)
        }
    }

    fun addItem(item: ShoppingItem) {
        viewModelScope.launch(IO) {
            repository.insert(item)
        }
    }

}