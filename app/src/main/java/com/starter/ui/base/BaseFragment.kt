package com.starter.ui.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.starter.R
import com.starter.utils.StatusCodes

/**
Created by Mohammad Zaki
on Sep,28 2021
 **/
open class BaseFragment : Fragment() {

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    fun setOnClickListeners(onClickListener: View.OnClickListener, vararg views: View) {
        for (view in views)
            view.setOnClickListener(onClickListener)
    }

    fun showSnackBar(messageResId: Int, barStatus: Int) {
        showSnackBar(getString(messageResId), barStatus)
    }

    fun showSnackBar(message: String, barStatus: Int) {
        val restoreBar =
            Snackbar.make(
                (mContext as Activity).findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            )
        val view = restoreBar.view
        when (barStatus) {
            StatusCodes.SUCCESS -> view.setBackgroundColor(
                ContextCompat.getColor(mContext, R.color.success)
            )
            StatusCodes.FAILED -> view.setBackgroundColor(
                ContextCompat.getColor(mContext, R.color.failure)
            )
            else -> {
            }
        }
        restoreBar.show()
    }

    fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(messageResId: Int) {
        showToast(getString(messageResId))
    }

}