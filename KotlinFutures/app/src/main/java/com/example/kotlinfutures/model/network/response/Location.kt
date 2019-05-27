package com.example.kotlinfutures.model.network.response

import com.google.gson.annotations.SerializedName

data class Location(
        @field:SerializedName("city") val city: String,
        @field:SerializedName("street") val street: String,
        @field:SerializedName("timezone") val timezone: Timezone,
        @field:SerializedName("postcode") val postcode: String,
        @field:SerializedName("coordinates") val coordinates: Coordinates,
        @field:SerializedName("state") val state: String
)
