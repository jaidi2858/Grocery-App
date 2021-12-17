package com.junaid.groceryappassignment.model.source.roomDataSource


import androidx.room.Dao
import androidx.room.Query
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem

@Dao
interface GroceryItemDao : BaseDao<GroceryItem> {

    @Query("SELECT * from grocery_item where groceryId = :groceryId ")
    fun getAllGroceryItemList(groceryId:Long): List<GroceryItem>

    @Query("DELETE from grocery_item where groceryId = :groceryId ")
    fun deleteAllGroceryItemList(groceryId:Long)

}