package com.skaravatskiy.randomusers.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.skaravatskiy.randomusers.data.model.database.User
import io.reactivex.disposables.CompositeDisposable

abstract class RxViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

interface BaseViewModel {
     fun getData(): LiveData<List<User>>
}
