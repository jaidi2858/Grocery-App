package com.junaid.groceryappassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.junaid.groceryappassignment.utils.general.OneShotEvent

open class BaseViewModel() : ViewModel() {


    val snackBarMessage = MutableLiveData<OneShotEvent<String>>()
    val progressBar = MutableLiveData<OneShotEvent<Boolean>>()


    protected fun showSnackBarMessage(message: String) {
        snackBarMessage.value = OneShotEvent(message)
    }




    protected fun showProgressBar(show: Boolean) {
        progressBar.value = OneShotEvent(show)
    }




}