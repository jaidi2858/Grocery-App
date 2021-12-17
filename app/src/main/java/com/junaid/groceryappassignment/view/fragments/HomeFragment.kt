package com.junaid.groceryappassignment.view.fragments

import androidx.fragment.app.viewModels
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.FragmentHomeBinding
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.utils.application.gone
import com.junaid.groceryappassignment.utils.application.showSnackBar
import com.junaid.groceryappassignment.utils.application.visible
import com.junaid.groceryappassignment.utils.general.AppConstants
import com.junaid.groceryappassignment.view.adapters.BaseAdapter
import com.junaid.groceryappassignment.view.adapters.CurrentGroceryItemsAdapter
import com.junaid.groceryappassignment.view.dialog.ItemOptionsDialog
import com.junaid.groceryappassignment.viewModel.GroceryItemViewModel
import com.junaid.groceryappassignment.viewModel.GroceryListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home),BaseAdapter.OnItemClicker {

    private lateinit var binding: FragmentHomeBinding

    private var groceryListItem:GroceryListItem ?= null
    private val allGroceryItems:ArrayList<GroceryItem> = ArrayList()
    private val allGroceryItemsAdapter by lazy { CurrentGroceryItemsAdapter(this) }

    private val groceryItemViewModel by viewModels<GroceryItemViewModel>()

    override fun setupViews() {
        binding= FragmentHomeBinding.bind(parentView)
        binding.rvGroceryItems.adapter=allGroceryItemsAdapter
    }

    override fun attachViewModel() {
        super.attachViewModel()
        with(groceryItemViewModel){
            setupGeneralViewModel(this)

            snackBarMessage.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    showSnackBar(it)
                    binding.tvErrorMessage.visible()
                    binding.rvGroceryItems.gone()
                }
            }
            allGroceryItemsLiveData.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    allGroceryItems.clear()
                    allGroceryItems.addAll(it.second)
                    allGroceryItemsAdapter.updateItemList(allGroceryItems)
                    groceryListItem=it.first
                    binding.tvFragmentTitle.text=""+getString(R.string.grocery_list)+" #"+groceryListItem?.id
                    binding.tvErrorMessage.gone()
                    binding.rvGroceryItems.visible()

                }
            }
            groceryItemUpdateLiveData.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    allGroceryItemsAdapter.updateItem(it,allGroceryItems[it])
                }
            }
            groceryClearLiveData.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    allGroceryItems.clear()
                    allGroceryItemsAdapter.clear()
                    binding.tvFragmentTitle.text=getString(R.string.grocery_list)
                    showSnackBar("Your grocery completed successfully")
                    binding.tvErrorMessage.visible()
                    binding.rvGroceryItems.gone()
                }
            }
            getLatestPendingGrocery()
        }
    }

    override fun onItemClick(position: Int, data: Any?) {
        val itemOptions = ItemOptionsDialog {
            val pendingCount=allGroceryItems.filter { it.groceryStatus==AppConstants.GROCERY_STATUS.PENDING_STATUS }.count()
            allGroceryItems[position].groceryStatus = AppConstants.GROCERY_STATUS.Completed_STATUS
            if(pendingCount>1) {
                groceryItemViewModel.updateGroceryItem(allGroceryItems[position], position)
            } else {
                groceryListItem?.groceryStatus=AppConstants.GROCERY_STATUS.Completed_STATUS
                groceryItemViewModel.updateGroceryItem(groceryListItem!!,allGroceryItems[position])
            }
        }
        itemOptions.show(childFragmentManager, "")
    }

}