package com.example.internwork3.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url : String?) {
    Glide.with(this.context).load(url).into(this)
}

fun View.visible() {
    visibility = View.VISIBLE // View'ı görünür yapar

}