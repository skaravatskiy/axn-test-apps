package com.skaravatskiy.randomusers.data.model.poko

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String
)
