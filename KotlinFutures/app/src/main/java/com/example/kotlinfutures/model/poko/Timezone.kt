package com.example.kotlinfutures.model.poko

import com.google.gson.annotations.SerializedName

data class Timezone(

    @field:SerializedName("offset")
    val offset: String,

    @field:SerializedName("description")
    val description: String
)