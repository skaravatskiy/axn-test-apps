package com.skaravatskiy.randomusers.data.model.poko

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("last") val last: String,
    @SerializedName("title") val title: String,
    @SerializedName("first") val first: String
)
