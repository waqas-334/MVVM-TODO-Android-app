package com.waqasyounis.mvvm.shopping.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ShoppingItemRepositoryImpl
) :
    ViewModel() {


    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

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
            tasksEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(item))
        }
    }

    fun onUndoDelete(item: ShoppingItem) {
        viewModelScope.launch(IO) {
            repository.insert(item)
        }
    }

    fun addItem(item: ShoppingItem) {
        viewModelScope.launch(IO) {
            repository.insert(item)
        }
    }

    fun sortSelected(sortOrder: SortOrder) {
        viewModelScope.launch(IO) {
            repository.sort(sortOrder)
        }
    }

    sealed class TasksEvent {
        data class ShowUndoDeleteTaskMessage(val item: ShoppingItem) : TasksEvent()
    }


}