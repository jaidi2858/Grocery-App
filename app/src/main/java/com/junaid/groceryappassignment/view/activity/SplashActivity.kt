package com.junaid.groceryappassignment.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.utils.general.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch(Dispatchers.IO) {
            delay(AppConstants.SPLASH_DELAY)
            withContext(Dispatchers.Main){
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}