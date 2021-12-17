package com.junaid.groceryappassignment.view.adapters

import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.RvCurrentGroceryItemBinding
import com.junaid.groceryappassignment.databinding.RvGroceryItemBinding
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.utils.application.color
import com.junaid.groceryappassignment.utils.general.AppConstants


class CurrentGroceryItemsAdapter(
    private val itemClicker: BaseAdapter.OnItemClicker,
) : BaseAdapter<GroceryItem>(itemClicker, R.layout.rv_current_grocery_item) {

    override fun View.bind(item: GroceryItem, position: Int) {
        val binding = RvCurrentGroceryItemBinding.bind(this)
        with(binding) {
            tvGroceryName.text = item.groceryText
            tvGroceryPrice.text= "${item.groceryPrice} PKR"
            if(item.groceryStatus==AppConstants.GROCERY_STATUS.PENDING_STATUS){
                clGroceryDetails.setBackgroundResource(R.drawable.bg_grocery_pending)
            } else {
                clGroceryDetails.setBackgroundResource(R.drawable.bg_grocery_completed)
            }
        }

    }

}