package com.example.kotlinfutures.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    val nameFirst: String,
    val nameLast: String,
    val age: Int,
    val email: String,
    val dob: String,
    val cell: String,
    val location: String,
    val gender: String,
    var login: String,
    val pass: String
) : Parcelable {
    companion object {
        val EXTRA_LOGIN: String = "EXTRA_USER"
        val EXTRA_ENCRYPT: String = "EXTRA_ENCRYPT"
    }
}