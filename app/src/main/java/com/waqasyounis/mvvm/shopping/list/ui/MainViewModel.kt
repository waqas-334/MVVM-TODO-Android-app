package com.waqasyounis.mvvm.shopping.list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepositoryImpl
import com.waqasyounis.mvvm.shopping.list.util.DummyData.Companion.shoppingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.asLiveData

data class MainUiModel(val listOfItems: List<ShoppingItem>, val sortOrder: SortOrder)


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ShoppingItemRepositoryImpl
) :
    ViewModel() {


    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    private val sortOrderFlow: MutableStateFlow<SortOrder> = MutableStateFlow(SortOrder.NONE)
    private val allFlow: Flow<List<ShoppingItem>> = repository.getAllItem()

    private val uiModelFlow: Flow<MainUiModel> =
        combine(sortOrderFlow, allFlow) { order, shoppingItems ->
            val sortedList = when(order){
                SortOrder.QUANTITY_ASC -> shoppingItems.sortedBy { it.noOfItems }
                SortOrder.QUANTITY_DESC -> shoppingItems.sortedByDescending { it.noOfItems }
                SortOrder.NONE -> shoppingItems

            }
            return@combine MainUiModel(
                sortedList,
                order
            )
        }


    val itemUiModel = uiModelFlow.asLiveData()

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
        sortOrderFlow.value = sortOrder
    }

    sealed class TasksEvent {
        data class ShowUndoDeleteTaskMessage(val item: ShoppingItem) : TasksEvent()
    }


}