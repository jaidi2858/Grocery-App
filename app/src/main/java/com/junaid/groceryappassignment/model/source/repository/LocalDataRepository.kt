package com.junaid.groceryappassignment.model.source.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.model.source.roomDataSource.GroceryItemDao
import com.junaid.groceryappassignment.model.source.roomDataSource.GroceryListDao
import com.junaid.groceryappassignment.model.source.roomDataSource.GroceryRoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LocalDataRepository @Inject constructor(@ApplicationContext private val context: Context){

    private val groceryListDao: GroceryListDao = GroceryRoomDatabase.getDatabase(context).groceryListDao()
    private val groceryItemDao: GroceryItemDao = GroceryRoomDatabase.getDatabase(context).groceryItemDao()

    // ====================================== Grocery List Flow =================================

    suspend fun insertGroceryList(groceryListItem: GroceryListItem):Long {
        return groceryListDao.insert(groceryListItem)
    }

    suspend fun deleteGroceryList(groceryListItem:GroceryListItem) {
        return groceryListDao.delete(groceryListItem)
    }
    suspend fun updateGroceryList(groceryListItem:GroceryListItem) {
        return groceryListDao.update(groceryListItem)
    }
    suspend fun getAllGroceryList() : List<GroceryListItem> {
       return groceryListDao.getAllGroceryList()
    }

    suspend fun getLatestPendingGrocery() : GroceryListItem? {
        return groceryListDao.getLatestPendingGrocery()
    }


    // ====================================== Grocery Item Flow =================================


    suspend fun insertGroceryItem(groceryItem: GroceryItem):Long {
        return groceryItemDao.insert(groceryItem)
    }
    suspend fun updateGroceryItem(groceryItem: GroceryItem) {
        return groceryItemDao.update(groceryItem)
    }
    suspend fun insertGroceryItems(groceryItems: ArrayList<GroceryItem>) {
        return groceryItemDao.insert(groceryItems)
    }

    suspend fun deleteGroceryItems(groceryListItemId: Long) {
        return groceryItemDao.deleteAllGroceryItemList(groceryListItemId)
    }

    suspend fun getAllGroceryItemList(groceryListItemId:Long) : List<GroceryItem> {
        return groceryItemDao.getAllGroceryItemList(groceryListItemId)
    }





}