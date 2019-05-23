package com.skaravatskiy.randomusers.data.model.poko

import com.google.gson.annotations.SerializedName

data class RandomUserInfo(
    @SerializedName("nat") val nat: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("dob") val dob: Dob,
    @SerializedName("name") val name: Name,
    @SerializedName("registered") val registered: Registered,
    @SerializedName("location") val location: Location,
    @SerializedName("id") val id: Id,
    @SerializedName("login") val login: Login,
    @SerializedName("cell") val cell: String,
    @SerializedName("email") val email: String,
    @SerializedName("picture") val picture: Picture
)
