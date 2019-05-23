package com.skaravatskiy.randomusers.data.model.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BindingAdapter
import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skaravatskiy.randomusers.R
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    val nameFirst: String,
    val nameLast: String,
    val age: Int,
    val email: String,
    val dob: String,
    val cell: String,
    val location: String,
    val gender: String,
    val picture: String
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val EXTRA_USER: String = "EXTRA_USER"

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun imageUrl(imageView: ImageView, url: String) {
            Glide.with(imageView.context)
                .asBitmap()
                .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .load(url)
                .into(imageView)
        }
    }
}
