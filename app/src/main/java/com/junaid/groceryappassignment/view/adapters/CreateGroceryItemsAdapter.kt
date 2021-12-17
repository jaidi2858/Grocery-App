package com.junaid.groceryappassignment.view.adapters

import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.RvGroceryItemBinding
import com.junaid.groceryappassignment.model.data.GroceryItem


class CreateGroceryItemsAdapter(
    private val itemClicker: BaseAdapter.OnItemClicker,
) : BaseAdapter<GroceryItem>(null, R.layout.rv_grocery_item) {

    override fun View.bind(item: GroceryItem, position: Int) {
        val binding = RvGroceryItemBinding.bind(this)
        with(binding) {
            tvGroceryName.text = item.groceryText
            tvGroceryPrice.text= "${item.groceryPrice} PKR"
            ivOptions.setOnClickListener {
                itemClicker.onItemClick(position,item)
            }
        }

    }

}