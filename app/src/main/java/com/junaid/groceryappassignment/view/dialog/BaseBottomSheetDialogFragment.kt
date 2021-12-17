package com.junaid.groceryappassignment.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junaid.groceryappassignment.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
    }

    override fun onStart() {
        dialog?.let { d -> BottomSheetBehavior.from(d.findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).state = BottomSheetBehavior.STATE_EXPANDED }

        super.onStart()
    }

    abstract fun getLayout():Int
    abstract fun initUi(view: View)







}