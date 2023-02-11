package com.waqasyounis.mvvm.shopping.list.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem
import com.waqasyounis.mvvm.shopping.list.ui.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingItem: ShoppingItem)

    @Update
    fun update(shoppingItem: ShoppingItem)

    @Delete
    fun delete(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shoppingitem")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Insert
    fun insertItems(shoppingItems: List<ShoppingItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: ShoppingItem)

    fun sort(sortOrder: SortOrder) = when(sortOrder){
        SortOrder.QUANTITY_ASC -> sortByQuantityAsc()
        SortOrder.QUANTITY_DESC -> sortByQuantityDesc()
        else -> {}
    }

    @Query("SELECT * FROM shoppingitem ORDER BY noOfItems ASC")
    abstract fun sortByQuantityAsc(): LiveData<List<ShoppingItem>>

    @Query("SELECT * FROM shoppingitem ORDER BY noOfItems DESC")
    abstract fun sortByQuantityDesc(): LiveData<List<ShoppingItem>>

}