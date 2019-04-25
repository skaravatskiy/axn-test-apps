package com.example.kotlinfutures.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.kotlinfutures.model.Repository
import com.example.kotlinfutures.model.RepositoryDefault
import com.example.kotlinfutures.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val repository: Repository = RepositoryDefault()

    lateinit var userData: MutableLiveData<User>
    fun getData(): MutableLiveData<User> {
        userData = MutableLiveData()
        loadData()
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