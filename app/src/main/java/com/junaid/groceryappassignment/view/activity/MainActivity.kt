package com.junaid.groceryappassignment.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junaid.groceryappassignment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}