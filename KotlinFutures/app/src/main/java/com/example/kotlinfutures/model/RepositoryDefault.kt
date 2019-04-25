package com.example.kotlinfutures.model

import android.util.Log
import com.example.kotlinfutures.model.network.ApiAccess
import com.example.kotlinfutures.model.network.Retrofit
import com.example.kotlinfutures.model.poko.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.suspendCoroutine

class RepositoryDefault : Repository {

    private val baseUrl = "https://randomuser.me/"
    private val retrofit = Retrofit().getRetrofit(baseUrl)
    val api: ApiAccess = retrofit.create(ApiAccess::class.java)

    private suspend fun getRemoteUser() = suspendCoroutine<User> { continuation ->
        api.getUser().enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("CoroutinesExeption", t.message)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body()
                continuation.resumeWith(Result.success(response.body()!!.results[0]).map {
                    Mapper.toUser(it)
                })
            }
        })
    }

    private suspend fun getLocalUser(): User? = null

    override suspend fun getUser(): User =
        if (getLocalUser() != null) getLocalUser()!! else getRemoteUser()

}