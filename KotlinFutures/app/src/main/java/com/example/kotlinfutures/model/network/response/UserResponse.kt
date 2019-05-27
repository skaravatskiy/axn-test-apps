package com.example.kotlinfutures.model.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
        @field:SerializedName("results") val results: List<RandomUserInfo>,
        @field:SerializedName("info") val info: Info
)
