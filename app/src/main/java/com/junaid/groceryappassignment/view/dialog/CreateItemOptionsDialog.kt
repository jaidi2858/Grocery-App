package com.junaid.groceryappassignment.view.dialog


import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.DialogCreateItemOptionsBinding
import com.junaid.groceryappassignment.databinding.DialogListOptionsBinding

class CreateItemOptionsDialog(private val optionSelector: OptionSelector) :
    BaseBottomSheetDialogFragment() {


    private lateinit var binding: DialogCreateItemOptionsBinding
    override fun getLayout(): Int {
        return R.layout.dialog_create_item_options
    }

    override fun initUi(view: View) {
        binding = DialogCreateItemOptionsBinding.bind(view)
        binding.tvCancel.setOnClickListener {
            this.dismiss()
        }
        binding.tvEdit.setOnClickListener {
            optionSelector.onSelected(0)
            this.dismiss()
        }
        binding.tvDelete.setOnClickListener {
            optionSelector.onSelected(1)
            this.dismiss()
        }
    }

    fun interface OptionSelector {
        fun onSelected(position: Int)
    }


}