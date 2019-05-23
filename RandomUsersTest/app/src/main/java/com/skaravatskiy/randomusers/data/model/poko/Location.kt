package com.skaravatskiy.randomusers.data.model.poko

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city") val city: String,
    @SerializedName("street") val street: String,
    @SerializedName("timezone") val timezone: Timezone,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("coordinates") val coordinates: Coordinates,
    @SerializedName("state") val state: String
)
