package com.example.kotlinfutures.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val loginStatus = MutableLiveData<Boolean>()
    fun isLogin(status: Boolean) {
        loginStatus.value = status
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return loginStatus
    }
}
