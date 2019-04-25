package com.example.kotlinfutures.model.poko

import com.google.gson.annotations.SerializedName

data class Registered(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("age")
    val age: Int
)