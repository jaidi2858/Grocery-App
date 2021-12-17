package com.junaid.groceryappassignment.view.dialog


import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.DialogCreateGroceryItemBinding
import com.junaid.groceryappassignment.model.data.GroceryItem
import com.junaid.groceryappassignment.utils.application.showSnackBar
import com.junaid.groceryappassignment.utils.application.string
import com.junaid.groceryappassignment.view.fragments.CreateGroceryFragment

class CreateItemDialog() : BaseDialog(), View.OnClickListener {


    private lateinit var binding: DialogCreateGroceryItemBinding

    var groceryItem: GroceryItem? = null

    override fun getLayout(): Int {
        return R.layout.dialog_create_grocery_item
    }

    override fun initUi(view: View) {
        binding = DialogCreateGroceryItemBinding.bind(view)
        binding.btnAddItem.setOnClickListener(this)
        binding.ivClose.setOnClickListener(this)
        dialog?.setCanceledOnTouchOutside(true)
        groceryItem?.let {
            binding.btnAddItem.text=getString(R.string.update_item)
            binding.etItemName.setText(it.groceryText)
            binding.etItemPrice.setText(it.groceryPrice)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivClose -> {
                this.dismissAllowingStateLoss()
            }
            R.id.btnAddItem -> {
                validateItem()
            }
        }
    }


    private fun validateItem() {
        if (binding.etItemName.string().isEmpty()) {
            showSnackBar(getString(R.string.item_name_required))
            binding.etItemName.requestFocus()
        } else if (binding.etItemPrice.string().isEmpty()) {
            showSnackBar(getString(R.string.item_price_required))
            binding.etItemPrice.requestFocus()
        } else {
            if (groceryItem == null) {
                (requireParentFragment() as CreateGroceryFragment).addGroceryItem(
                    binding.etItemName.string(),
                    binding.etItemPrice.string()
                )
            } else {
                groceryItem?.groceryText=binding.etItemName.string()
                groceryItem?.groceryPrice=binding.etItemPrice.string()
                (requireParentFragment() as CreateGroceryFragment).updateGroceryItem()
            }
            this.dismiss()
        }
    }


}