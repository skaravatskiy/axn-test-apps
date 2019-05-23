package com.skaravatskiy.randomusers.data.repository

import com.skaravatskiy.randomusers.data.model.database.User
import io.reactivex.Observable

interface Repository {
    fun getUsers(): Observable<List<User>>
}
