package com.junaid.groceryappassignment.utils.application

import android.app.Application
import android.content.Context

import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class GroceryApp : Application() {


    override fun onCreate() {
        super.onCreate()
    }



}