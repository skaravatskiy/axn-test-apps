package com.example.kotlinfutures.model.network

import com.example.kotlinfutures.model.poko.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiAccess {

    @GET("api")
    fun getUser(): Call<UserResponse>
}