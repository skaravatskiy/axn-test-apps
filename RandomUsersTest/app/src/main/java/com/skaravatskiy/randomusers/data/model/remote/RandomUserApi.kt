package com.skaravatskiy.randomusers.data.model.remote

import com.skaravatskiy.randomusers.data.model.common.Const
import com.skaravatskiy.randomusers.data.model.poko.UsersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET(Const.API)
    fun getUser(@Query(Const.QUERY_USERS) numberOfUsers: Int): Observable<UsersResponse>
}
