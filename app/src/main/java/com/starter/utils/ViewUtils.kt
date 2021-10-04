package com.starter.utils

import android.content.Context
import android.util.DisplayMetrics

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/
object ViewUtils {

    /**
     * Method to convert dp into pixel
     *
     * @param context of calling activity or fragment
     * @param dp      dp value that need to convert into px
     * @return px converted dp into px
     */
    fun dpToPx(context: Context?, dp: Int): Int {
        return Math.round(dp * getPixelScaleFactor(context!!))
    }

    /**
     * Method to convert pixel into dp
     *
     * @param context of calling activity or fragment
     * @param px      pixel value that need to convert into dp
     * @return dp converted px into dp
     */
    fun pxToDp(context: Context?, px: Int): Int {
        return Math.round(px / getPixelScaleFactor(context!!))
    }

    /**
     * @param context of calling activity or fragment
     * @return pixel scale factor
     */
    private fun getPixelScaleFactor(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
    }
}