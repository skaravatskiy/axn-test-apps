package com.example.kotlinfutures.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.kotlinfutures.model.local.Repository
import com.example.kotlinfutures.model.local.RepositoryDefault
import com.example.kotlinfutures.model.local.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val repository: Repository =
        RepositoryDefault()
    lateinit var userData: MutableLiveData<User>

    fun getData(): MutableLiveData<User> {
        userData = MutableLiveData()
        return userData
    }

    fun loadData() {
        GlobalScope.launch {
            val user = repository.getUser()
            updateUser(user)
        }
    }

    private fun updateUser(user: User) {
        userData.postValue(user)
    }
}
