package com.junaid.groceryappassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.model.source.repository.LocalDataRepository
import com.junaid.groceryappassignment.utils.general.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroceryItemViewModel @Inject constructor(
    private val localDataRepository: LocalDataRepository
) : BaseViewModel() {

    var allGroceryItemsLiveData: MutableLiveData<OneShotEvent<Pair<GroceryListItem,ArrayList<GroceryItem>>>> = MutableLiveData()
    var specificGroceryItemsLiveData: MutableLiveData<OneShotEvent<ArrayList<GroceryItem>>> = MutableLiveData()
    var groceryItemUpdateLiveData: MutableLiveData<OneShotEvent<Int>> = MutableLiveData()
    var groceryClearLiveData: MutableLiveData<OneShotEvent<Boolean>> = MutableLiveData()
    var groceryItemInsertLiveData: MutableLiveData<OneShotEvent<GroceryItem>> = MutableLiveData()


    fun getLatestPendingGrocery() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentGroceryItem = localDataRepository.getLatestPendingGrocery()
            if (currentGroceryItem == null) {
                withContext(Dispatchers.Main) {
                    showSnackBarMessage("No Pending grocery found")
                }
            } else {
                val allGroceryItems = localDataRepository.getAllGroceryItemList(currentGroceryItem?.id)
                withContext(Dispatchers.Main) {
                    allGroceryItemsLiveData.value = OneShotEvent(Pair(currentGroceryItem,ArrayList(allGroceryItems)))
                }
            }
        }
    }

    fun updateGroceryItem(groceryItem: GroceryItem, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataRepository.updateGroceryItem(groceryItem)
            withContext(Dispatchers.Main) {
                groceryItemUpdateLiveData.value = OneShotEvent(position)
            }
        }
    }

    fun updateGroceryItem(groceryListItem: GroceryListItem, groceryItem: GroceryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataRepository.updateGroceryList(groceryListItem)
            localDataRepository.updateGroceryItem(groceryItem)
            withContext(Dispatchers.Main) {
                groceryClearLiveData.value = OneShotEvent(true)
            }
        }
    }

    fun getAllGroceryItemsByListId(groceryListItemId:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val groceryList=localDataRepository.getAllGroceryItemList(groceryListItemId)
            withContext(Dispatchers.Main) {
                specificGroceryItemsLiveData.value = OneShotEvent(ArrayList(groceryList))
            }
        }
    }

    fun insertNewGroceryItem(groceryItem: GroceryItem){
        viewModelScope.launch(Dispatchers.IO) {
            val newInsertedId=localDataRepository.insertGroceryItem(groceryItem)
            groceryItem.id=newInsertedId
            withContext(Dispatchers.Main){
                groceryItemInsertLiveData.value= OneShotEvent(groceryItem)
            }
        }

    }


}