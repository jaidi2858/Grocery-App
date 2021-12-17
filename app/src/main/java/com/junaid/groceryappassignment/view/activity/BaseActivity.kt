package com.junaid.groceryappassignment.view.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.junaid.groceryappassignment.utils.general.DialogUtils


abstract class BaseActivity() : AppCompatActivity() {


    lateinit var mProgressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog= DialogUtils.getProgressDialog(this)

    }


}