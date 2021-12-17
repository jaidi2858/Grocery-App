package com.junaid.groceryappassignment.view.adapters

import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.RvGroceryItemBinding
import com.junaid.groceryappassignment.databinding.RvGroceryListItemBinding
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.model.data.GroceryListItem


class AllGroceryListAdapter(
    private val itemClicker: BaseAdapter.OnItemClicker,
) : BaseAdapter<GroceryListItem>(itemClicker, R.layout.rv_grocery_list_item) {

    override fun View.bind(item: GroceryListItem, position: Int) {
        val binding = RvGroceryListItemBinding.bind(this)
        with(binding) {
            tvGroceryNumber.text = "Grocery No "+item.id
            tvGroceryDate.text = "Date : "+item.getFormattedDate()
            tvGroceryStatus.text= "Status : "+item.groceryStatus
        }

    }

}