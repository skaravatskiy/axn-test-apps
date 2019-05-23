package com.skaravatskiy.randomusers.data.repository

import com.skaravatskiy.randomusers.App
import com.skaravatskiy.randomusers.data.model.common.Const
import com.skaravatskiy.randomusers.data.model.common.Mapper
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.data.model.poko.UsersResponse
import com.skaravatskiy.randomusers.data.model.remote.RandomUserApi
import com.skaravatskiy.randomusers.data.model.remote.Retrofit
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class RepositoryDefault : Repository {
    private val retrofit = Retrofit().getRetrofit(Const.BASE_URL)
    private val api = retrofit.create(RandomUserApi::class.java)

    override fun getUsers(): Observable<List<User>> = getLocalUsers().toObservable()
        .flatMap { if (it.isEmpty()) getRemoteUsers() else Observable.just(it) }

    private fun saveToDataBase(usersList: List<User>) {
        App.appDatabaseInstance()
            .daoAccess()
            .insert(usersList)
    }

    private fun getRemoteUsers(): Observable<List<User>> {
        return api.getUser(Const.NUMBER_OF_USERS)
            .map { usersResponse: UsersResponse -> Mapper.toUser(usersResponse.results) }
            .flatMap {
                val saveResult = Observable.just(saveToDataBase(it))
                val result = Observable.just(it)
                Observable.zip(saveResult, result, BiFunction { _: Unit, usersList: List<User> -> usersList })
            }
            .flatMap { usersList -> Observable.just(usersList) }
    }

    private fun getLocalUsers() = App.appDatabaseInstance().daoAccess().getAll()

}

