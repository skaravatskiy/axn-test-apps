package com.skaravatskiy.randomusers.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skaravatskiy.randomusers.data.model.database.User

class DetailViewModel : ViewModel() {
    private var userData: MutableLiveData<User>? = null

    fun getData(): LiveData<User> {
        if (userData == null) {
            userData = MutableLiveData()
        }
        return userData as MutableLiveData<User>
    }
    fun setUser(user: User){
        userData?.value = user
    }
}
