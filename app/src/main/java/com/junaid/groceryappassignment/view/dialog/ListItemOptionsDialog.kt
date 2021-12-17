package com.junaid.groceryappassignment.view.dialog


import android.view.View
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.DialogListOptionsBinding

class ListItemOptionsDialog(private val optionSelector: OptionSelector) :
    BaseBottomSheetDialogFragment() {


    private lateinit var binding: DialogListOptionsBinding

    override fun getLayout(): Int {
        return R.layout.dialog_list_options
    }

    override fun initUi(view: View) {
        binding = DialogListOptionsBinding.bind(view)
        binding.tvCancel.setOnClickListener {
            this.dismiss()
        }
        binding.tvEdit.setOnClickListener {
            optionSelector.onSelected(0)
            this.dismiss()
        }
        binding.tvCopy.setOnClickListener {
            optionSelector.onSelected(1)
            this.dismiss()
        }
        binding.tvDelete.setOnClickListener {
            optionSelector.onSelected(2)
            this.dismiss()
        }
    }

    fun interface OptionSelector {
        fun onSelected(position: Int)
    }


}