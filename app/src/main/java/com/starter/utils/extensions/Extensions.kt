package com.starter.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide


fun Activity.openActivity(name: Class<*>, isFinish: Boolean) {
    val intent = Intent(this, name)
    startActivity(intent)
    if (isFinish) this.finish()
}


fun Activity.openActivity(name: Class<*>, isFinish: Boolean, bundle: Bundle) {
    val intent = Intent(this, name)
    intent.putExtras(bundle)
    startActivity(intent)
    if (isFinish) this.finish()
}

fun Activity.openActivityForResult(name: Class<*>, bundle: Bundle, requestCode: Int) {
    val intent = Intent(this, name)
    intent.putExtras(bundle)
    startActivityForResult(intent, requestCode)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun SwipeRefreshLayout.stopRefreshing() {
    if (this.isRefreshing) this.isRefreshing = false
}

fun ImageView.loadImage(url: String?, mContext: Context) {
    if (url.isNullOrEmpty()) {
        /*Glide.with(mContext)
            .load(ContextCompat.getDrawable(mContext, R.drawable.image_placeholder))
            .into(this)*/
    } else {
        Glide.with(mContext)
            .load(url)
            .into(this)
    }
}

fun Activity.switchTheme() {
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES ->
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Configuration.UI_MODE_NIGHT_NO ->
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}

