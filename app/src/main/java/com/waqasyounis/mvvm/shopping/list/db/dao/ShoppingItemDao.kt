package com.waqasyounis.mvvm.shopping.list.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.waqasyounis.mvvm.shopping.list.db.entities.ShoppingItem

@Dao
interface ShoppingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingItem: ShoppingItem)

    @Update
    fun update(shoppingItem: ShoppingItem)

    @Delete
    fun delete(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shoppingitem")
    fun getAllItems(): LiveData<List<ShoppingItem>>

    @Insert
    fun insertItems(shoppingItems: List<ShoppingItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: ShoppingItem)

}