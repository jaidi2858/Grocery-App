package com.junaid.groceryappassignment.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.junaid.groceryappassignment.R


abstract class BaseDialog(): DialogFragment() {




    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.GeneralDialogStyle);
        val builder = AlertDialog.Builder(requireContext(), R.style.GeneralDialogStyle)
        val inflater = requireActivity().layoutInflater
        val dialog = inflater.inflate(getLayout(), null)
        initUi(dialog)
        builder.setView(dialog)
        return builder.create()
    }



    abstract fun initUi(view: View)

    abstract fun getLayout():Int

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0.5f
        window?.attributes = windowParams
    }








}