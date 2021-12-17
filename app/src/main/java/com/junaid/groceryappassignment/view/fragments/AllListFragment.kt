package com.junaid.groceryappassignment.view.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.FragmentAllListBinding
import com.junaid.groceryappassignment.model.data.GroceryListItem
import com.junaid.groceryappassignment.utils.application.gone
import com.junaid.groceryappassignment.utils.application.showSnackBar
import com.junaid.groceryappassignment.utils.application.visible
import com.junaid.groceryappassignment.utils.general.AppConstants
import com.junaid.groceryappassignment.view.adapters.AllGroceryListAdapter
import com.junaid.groceryappassignment.view.adapters.BaseAdapter
import com.junaid.groceryappassignment.view.dialog.ListItemOptionsDialog
import com.junaid.groceryappassignment.viewModel.GroceryListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllListFragment : BaseFragment(R.layout.fragment_all_list), BaseAdapter.OnItemClicker {

    private lateinit var binding: FragmentAllListBinding
    private val parentNavController by lazy {
        requireParentFragment().requireParentFragment().findNavController()
    }

    private val allGroceryListItems: ArrayList<GroceryListItem> = ArrayList()
    private val allGroceryListAdapter by lazy { AllGroceryListAdapter(this) }
    private val groceryViewModel by viewModels<GroceryListViewModel>()

    override fun setupViews() {
        binding = FragmentAllListBinding.bind(parentView)
        binding.rvAllGroceryList.adapter = allGroceryListAdapter
        setupSwipeListener()
    }

    override fun attachViewModel() {
        super.attachViewModel()
        with(groceryViewModel) {
            setupGeneralViewModel(this)

            snackBarMessage.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    showSnackBar(it)
                    binding.tvErrorMessage.visible()
                    binding.rvAllGroceryList.gone()
                }
            }
            allGroceryListLiveData.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    allGroceryListItems.clear()
                    allGroceryListItems.addAll(it)
                    allGroceryListAdapter.updateItemList(allGroceryListItems)
                    binding.tvErrorMessage.gone()
                    binding.rvAllGroceryList.visible()
                }
            }
            groceryCopiedLiveData.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    allGroceryListItems.add(0, it)
                    allGroceryListAdapter.updateItemList(allGroceryListItems)
                }
            }
            groceryDeleteLiveData.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    showSnackBar("Your grocery successfully deleted")
                    allGroceryListItems.removeAt(it)
                    allGroceryListAdapter.updateItemList(allGroceryListItems)
                    if(allGroceryListItems.isNullOrEmpty()){
                        binding.tvErrorMessage.visible()
                        binding.rvAllGroceryList.gone()
                    }
                }
            }
            getAllGroceryListData()
        }

    }

    override fun onItemClick(position: Int, data: Any?) {
        val itemOptions = ListItemOptionsDialog {
            when (it) {
                0 -> {
                    val groceryListItem = data as GroceryListItem
                    if (groceryListItem.groceryStatus == AppConstants.GROCERY_STATUS.PENDING_STATUS) {
                        parentNavController.navigate(
                            MainFragmentDirections.actionNavMainFragmentToCreateGroceryFragment(
                                groceryListItem
                            )
                        )
                    } else {
                        showSnackBar("You can not edit completed grocery , Please duplicate it to edit")
                    }
                }
                1 -> {
                    groceryViewModel.copyGroceryListItem(data as GroceryListItem)
                }
                2 -> {
                    groceryViewModel.deleteGroceryListItem(data as GroceryListItem, position)
                }
            }
        }
        itemOptions.show(childFragmentManager, "")
    }


    private fun setupSwipeListener() {
        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    groceryViewModel.deleteGroceryListItem(allGroceryListItems[viewHolder.adapterPosition], viewHolder.adapterPosition)

                }
            })

        itemTouchHelper.attachToRecyclerView(binding.rvAllGroceryList)
    }

}