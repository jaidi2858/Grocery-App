package com.junaid.groceryappassignment.view.fragments


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.FragmentCreateGroceryBinding
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.utils.application.showSnackBar
import com.junaid.groceryappassignment.utils.general.AppConstants
import com.junaid.groceryappassignment.view.adapters.BaseAdapter
import com.junaid.groceryappassignment.view.adapters.CreateGroceryItemsAdapter
import com.junaid.groceryappassignment.view.dialog.CreateItemDialog
import com.junaid.groceryappassignment.view.dialog.CreateItemOptionsDialog
import com.junaid.groceryappassignment.viewModel.GroceryItemViewModel
import com.junaid.groceryappassignment.viewModel.GroceryListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CreateGroceryFragment : BaseFragment(R.layout.fragment_create_grocery),
    BaseAdapter.OnItemClicker, View.OnClickListener {

    private lateinit var binding: FragmentCreateGroceryBinding
    private val groceryViewModel by viewModels<GroceryListViewModel>()
    private val groceryItemViewModel by viewModels<GroceryItemViewModel>()

    private val groceryItemList: ArrayList<GroceryItem> = ArrayList()
    private val groceryAdapter by lazy { CreateGroceryItemsAdapter(this) }

    private val groceryListItem by lazy { CreateGroceryFragmentArgs.fromBundle(requireArguments()).grocerListItem }

    override fun setupViews() {
        binding = FragmentCreateGroceryBinding.bind(parentView)
        binding.ivAddGrocery.setOnClickListener(this)
        binding.btnSaveGrocery.setOnClickListener(this)
        binding.rvAllGroceryList.adapter = groceryAdapter
    }

    override fun attachViewModel() {
        super.attachViewModel()
        with(groceryViewModel) {
            setupGeneralViewModel(this)
            groceryInsertLiveData.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    showSnackBar("Grocery number $it added to your list")
                    findNavController().popBackStack()
                }
            }
            groceryUpdateLiveData.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    showSnackBar("Your grocery list successfully updated")
                    findNavController().popBackStack()
                }
            }
        }

        with(groceryItemViewModel){
            setupGeneralViewModel(this)
            specificGroceryItemsLiveData.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    groceryItemList.clear()
                    groceryItemList.addAll(it)
                    groceryAdapter.updateItemList(groceryItemList)
                }
            }
            groceryListItem?.let {
                getAllGroceryItemsByListId(it.id)
            }
        }
    }



    private fun createUpdateGroceryItem(groceryItem: GroceryItem?) {
        val groceryItemDialog = CreateItemDialog()
        groceryItemDialog.groceryItem = groceryItem
        groceryItemDialog.show(childFragmentManager, "")
    }

    fun addGroceryItem(itemName: String, itemPrice: String) {
        val groceryItem = GroceryItem(0, 0, itemName, itemPrice,AppConstants.GROCERY_STATUS.PENDING_STATUS)
        groceryListItem?.let {
            groceryItem.groceryId=it.id
        }
        groceryItemList.add(groceryItem)
        groceryAdapter.addItem(groceryItem)
    }

    fun updateGroceryItem() {
        groceryAdapter.updateItemList(groceryItemList)
    }


    override fun onItemClick(position: Int, data: Any?) {
        val itemOptions = CreateItemOptionsDialog {
            when (it) {
                0 -> {
                    createUpdateGroceryItem(groceryItemList[position])
                }
                1 -> {
                    groceryItemList.removeAt(position)
                    groceryAdapter.updateItemList(groceryItemList)
                }
            }
        }
        itemOptions.show(childFragmentManager, "")
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.ivAddGrocery -> {
                createUpdateGroceryItem(null)
            }
            binding.btnSaveGrocery -> {
                createOrUpdateGroceryList()
            }
        }
    }

    private fun createOrUpdateGroceryList() {
        if(groceryItemList.isNullOrEmpty()){
            showSnackBar("Please add some item to save grocery")
        } else if (groceryListItem==null) {
            val groceryListItem=GroceryListItem(0, Date(), AppConstants.GROCERY_STATUS.PENDING_STATUS)
            groceryViewModel.submitGroceryListItem(groceryListItem,groceryItemList)
        } else {
            groceryViewModel.updateGroceryListItem(groceryListItem!!,groceryItemList)
        }

    }



}