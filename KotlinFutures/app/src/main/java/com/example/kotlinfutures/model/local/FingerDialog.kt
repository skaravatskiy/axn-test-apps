package com.example.kotlinfutures.model.local

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.kotlinfutures.R

data class FingerDialog(var message: String,
                        var source: Int,
                        var color: Int){
    companion object{
        @BindingAdapter("imageDrawable")
        @JvmStatic
        fun imageDrawable(imageView: ImageView, src: Int) {
            imageView.setImageResource(src)
        }
    }
}
