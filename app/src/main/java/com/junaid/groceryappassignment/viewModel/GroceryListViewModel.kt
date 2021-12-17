package com.junaid.groceryappassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.model.source.repository.LocalDataRepository
import com.junaid.groceryappassignment.utils.general.AppConstants
import com.junaid.groceryappassignment.utils.general.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroceryListViewModel @Inject constructor(
    private val localDataRepository: LocalDataRepository
) : BaseViewModel() {

    var allGroceryListLiveData: MutableLiveData<OneShotEvent<ArrayList<GroceryListItem>>> =
        MutableLiveData()
    var groceryInsertLiveData: MutableLiveData<OneShotEvent<Long>> = MutableLiveData()
    var groceryCopiedLiveData: MutableLiveData<OneShotEvent<GroceryListItem>> = MutableLiveData()
    var groceryUpdateLiveData: MutableLiveData<OneShotEvent<Boolean>> = MutableLiveData()
    var groceryDeleteLiveData: MutableLiveData<OneShotEvent<Int>> = MutableLiveData()


    fun submitGroceryListItem(
        groceryListItem: GroceryListItem,
        groceryItems: ArrayList<GroceryItem>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val groceryItemListId = localDataRepository.insertGroceryList(groceryListItem)
            for (i in groceryItems.indices) {
                groceryItems[i].groceryId = groceryItemListId
            }
            localDataRepository.insertGroceryItems(groceryItems)
            withContext(Dispatchers.Main) {
                groceryInsertLiveData.value = OneShotEvent(groceryItemListId)
            }
        }
    }


    fun updateGroceryListItem(
        groceryListItem: GroceryListItem,
        groceryItems: ArrayList<GroceryItem>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataRepository.deleteGroceryItems(groceryListItem.id)
            localDataRepository.insertGroceryItems(groceryItems)
            withContext(Dispatchers.Main) {
                groceryUpdateLiveData.value = OneShotEvent(true)
            }
        }
    }


    fun copyGroceryListItem(groceryListItem: GroceryListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val groceryItems = localDataRepository.getAllGroceryItemList(groceryListItem.id)
            val updatedGroceryItem = groceryListItem.getDuplicatedItem()
            val newGroceryListId = localDataRepository.insertGroceryList(updatedGroceryItem)
            updatedGroceryItem.id = newGroceryListId
            for (i in groceryItems.indices) {
                groceryItems[i].id = 0
                groceryItems[i].groceryId = newGroceryListId
                groceryItems[i].groceryStatus = AppConstants.GROCERY_STATUS.PENDING_STATUS
            }
            localDataRepository.insertGroceryItems(ArrayList(groceryItems))
            withContext(Dispatchers.Main) {
                groceryCopiedLiveData.value = OneShotEvent(updatedGroceryItem)
            }
        }
    }

    fun deleteGroceryListItem(groceryListItem: GroceryListItem, groceryPosition: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataRepository.deleteGroceryItems(groceryListItem.id)
            localDataRepository.deleteGroceryList(groceryListItem)
            withContext(Dispatchers.Main)
            {
                groceryDeleteLiveData.value = OneShotEvent(groceryPosition)
            }
        }
    }


    fun getAllGroceryListData() {
        viewModelScope.launch(Dispatchers.IO) {
            val groceryListItems = localDataRepository.getAllGroceryList()
            withContext(Dispatchers.Main) {
                if (groceryListItems.isNullOrEmpty()) {
                    showSnackBarMessage("No grocery list found")
                } else {
                    allGroceryListLiveData.value = OneShotEvent(ArrayList(groceryListItems))
                }
            }
        }
    }


}