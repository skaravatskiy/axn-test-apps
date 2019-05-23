package com.skaravatskiy.randomusers.data.model.poko

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("results") val results: List<RandomUserInfo>,
    @SerializedName("info") val info: Info
)
