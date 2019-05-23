package com.skaravatskiy.randomusers.data.model.poko

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("seed") val seed: String,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: Int,
    @SerializedName("version") val version: String
)
