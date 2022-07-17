package com.waqasyounis.mvvm.shopping.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waqasyounis.mvvm.shopping.list.db.repository.ShoppingItemRepositoryImpl

@Suppress("UNCHECKED_CAST")
class ShoppingViewModelFactory(private val repository: ShoppingItemRepositoryImpl) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}