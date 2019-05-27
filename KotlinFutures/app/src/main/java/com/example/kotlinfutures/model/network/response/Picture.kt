package com.example.kotlinfutures.model.network.response

import com.google.gson.annotations.SerializedName

data class Picture(
        @field:SerializedName("thumbnail") val thumbnail: String,
        @field:SerializedName("large") val large: String,
        @field:SerializedName("medium") val medium: String
)
