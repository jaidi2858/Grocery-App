package com.junaid.groceryappassignment.view.dialog


import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.DialogItemOptionsBinding
import com.junaid.groceryappassignment.databinding.DialogListOptionsBinding

class ItemOptionsDialog(private val optionSelector: OptionSelector) :
    BaseBottomSheetDialogFragment() {


    private lateinit var binding: DialogItemOptionsBinding

    override fun getLayout(): Int {
        return R.layout.dialog_item_options
    }

    override fun initUi(view: View) {
        binding = DialogItemOptionsBinding.bind(view)
        binding.tvCancel.setOnClickListener {
            this.dismiss()
        }
        binding.tvMarkAsComplete.setOnClickListener {
            optionSelector.onSelected(0)
            this.dismiss()
        }

    }

    fun interface OptionSelector {
        fun onSelected(position: Int)
    }


}