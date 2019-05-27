package com.example.kotlinfutures.model.network.response

import com.google.gson.annotations.SerializedName

data class Info(
        @field:SerializedName("seed") val seed: String,
        @field:SerializedName("page") val page: Int,
        @field:SerializedName("results") val results: Int,
        @field:SerializedName("version") val version: String
)
