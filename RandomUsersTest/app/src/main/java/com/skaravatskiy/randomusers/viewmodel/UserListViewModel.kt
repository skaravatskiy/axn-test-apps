package com.skaravatskiy.randomusers.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.data.repository.Repository
import com.skaravatskiy.randomusers.data.repository.RepositoryDefault
import com.skaravatskiy.randomusers.ui.userslist.Sort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserListViewModel : RxViewModel(), BaseViewModel {
    var repository: Repository = RepositoryDefault()
    private var userData: MutableLiveData<List<User>>? = null
    lateinit var errorResponse: Throwable
    override fun getData(): LiveData<List<User>> {
        if (userData == null) {
            userData = MutableLiveData()
        }
        return userData as MutableLiveData<List<User>>
    }

    fun requestUsers() {
        val disposable = repository.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateUser,
                this::errorResolver)
        compositeDisposable.add(disposable)
    }

    private fun errorResolver(throwable: Throwable) {
//        if (BuildConfig.DEBUG) Log.d("errorResolver", throwable.message)
        errorResponse = throwable

    }

    fun sortBy(sort: Sort) {
        when (sort) {
            Sort.AGE -> userData?.value = userData?.value?.sortedWith(compareBy { it.age })
            Sort.NAME -> userData?.value = userData?.value?.sortedWith(compareBy { it.nameFirst })
            Sort.LOCATION -> userData?.value = userData?.value?.sortedWith(compareBy { it.location })
        }
    }

    fun updateUser(usersList: List<User>) {
        userData?.postValue(usersList)
    }
}
