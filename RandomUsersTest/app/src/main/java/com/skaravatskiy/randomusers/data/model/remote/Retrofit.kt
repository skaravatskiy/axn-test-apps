package com.skaravatskiy.randomusers.data.model.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    fun getRetrofit(url: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(initOkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(url)
        .build()

    private fun initOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(initLoggingInterceptor())
        .build()

    private fun initLoggingInterceptor() = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

}
