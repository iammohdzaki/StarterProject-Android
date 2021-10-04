package com.starter.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.starter.BuildConfig
import com.starter.utils.ViewUtils

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/

private const val OVERLAY_TEXT_SIZE_INT: Int = 15
private const val TEN = 10
private const val OVERLAY_TEXT: String =
    BuildConfig.FLAVOR + "_v" + BuildConfig.VERSION_CODE

class DrawOnTop(var mContext: Context?) : View(mContext) {

    private var paintText: Paint? = null
    private var bounds: Rect? = null

    init {
        paintText = Paint()
        bounds = Rect()
    }

    override fun onDraw(canvas: Canvas?) {
        paintText?.apply {
            color = Color.GRAY
            textSize = ViewUtils.dpToPx(mContext, OVERLAY_TEXT_SIZE_INT).toFloat()
            getTextBounds(OVERLAY_TEXT, 0, OVERLAY_TEXT.length, bounds)
        }
        canvas?.drawText(
            OVERLAY_TEXT,
            width.toFloat() - (bounds!!.width() + TEN),
            this.height.toFloat() - OVERLAY_TEXT_SIZE_INT,
            paintText!!
        )
    }
}