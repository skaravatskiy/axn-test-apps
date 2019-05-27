package com.example.kotlinfutures.model.network.response

import com.google.gson.annotations.SerializedName

data class Login(
        @field:SerializedName("sha1") val sha1: String,
        @field:SerializedName("password") val password: String,
        @field:SerializedName("salt") val salt: String,
        @field:SerializedName("sha256") val sha256: String,
        @field:SerializedName("uuid") val uuid: String,
        @field:SerializedName("username") val username: String,
        @field:SerializedName("md5") val md5: String
)
