package com.starter.ui.base

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.starter.BuildConfig
import com.starter.R
import com.starter.ui.custom.DrawOnTop
import com.starter.utils.StatusCodes


/**
Created by Mohammad Zaki
on Sep,28 2021
 **/
private const val TAG = "BaseActivity"

open class BaseActivity : AppCompatActivity() {

    fun showSnackBar(message: String, barStatus: Int) {
        val restoreBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val view = restoreBar.view
        when (barStatus) {
            StatusCodes.SUCCESS -> view.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.success
                )
            )
            StatusCodes.FAILED -> view.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.failure
                )
            )
            else -> {
            }
        }
        restoreBar.show()
    }


    fun showSnackBar(messageResId: Int, barStatus: Int) {
        showSnackBar(getString(messageResId), barStatus)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(messageResId: Int) {
        showToast(getString(messageResId))
    }

    fun setOnClickListeners(onClickListener: View.OnClickListener, vararg views: View) {
        for (view in views)
            view.setOnClickListener(onClickListener)
    }

    override fun onResume() {
        super.onResume()
        if(BuildConfig.WATER_MARK){
            val mDraw = DrawOnTop(this)
            addContentView(
                mDraw,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            mDraw.bringToFront()

        }
    }


}