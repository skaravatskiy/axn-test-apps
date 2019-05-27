package com.example.kotlinfutures.model.network

import com.example.kotlinfutures.model.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiAccess {

    @GET(Const.API)
    fun getUser(): Call<UserResponse>
}
