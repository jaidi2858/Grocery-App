package com.junaid.groceryappassignment.model.source.roomDataSource


import androidx.room.Dao
import androidx.room.Query
import com.junaid.groceryappassignment.model.data.GroceryListItem

@Dao
interface GroceryListDao : BaseDao<GroceryListItem> {

    @Query("SELECT * from grocery_list_item ORDER BY date DESC")
    fun getAllGroceryList(): List<GroceryListItem>

    @Query("SELECT * from grocery_list_item where status = 'Pending' ORDER BY date DESC limit 1")
    fun getLatestPendingGrocery(): GroceryListItem?

}