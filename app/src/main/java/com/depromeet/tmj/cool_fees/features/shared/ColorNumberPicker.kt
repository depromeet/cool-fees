package com.depromeet.tmj.cool_fees.features.shared

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.depromeet.tmj.cool_fees.R

class ColorNumberPicker : NumberPicker {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(ContextThemeWrapper(context, R.style.AppTheme_Picker), attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun addView(child: View?) {
        super.addView(child)
        child?.let { updateView(it) }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        child?.let { updateView(it) }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        child?.let { updateView(it) }
    }

    private fun updateView(view: View) {
        val colors = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf(-android.R.attr.state_selected)),
                intArrayOf(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.white))
        )
        if (view is EditText) {
            view.textSize = 25f
            view.setTextColor(colors)
        }
    }

    fun setTextColor(color: Int) {
        val selectorWheelPaintField = javaClass.superclass.getDeclaredField("mSelectorWheelPaint")
        selectorWheelPaintField.isAccessible = true
        (selectorWheelPaintField.get(this) as Paint).color = color

        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child is EditText)
                child.setTextColor(color)
        }
        invalidate()
    }
}