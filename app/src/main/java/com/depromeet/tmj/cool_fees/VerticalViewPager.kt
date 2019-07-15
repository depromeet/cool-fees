package com.depromeet.tmj.cool_fees

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Interpolator
import androidx.viewpager.widget.ViewPager

class VerticalViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {
    private var mScroller: ScrollerCustomDuration? = null

    init {
        init()
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return super.canScrollHorizontally(direction)
    }

    private fun init() {
        // Make page transit vertical
        setPageTransformer(true, VerticalPageTransformer())
        // Get rid of the overscroll drawing that happens on the left and right (the ripple)
        overScrollMode = View.OVER_SCROLL_NEVER
        postInitViewPager()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val toIntercept = super.onInterceptTouchEvent(flipXY(ev))
        // Return MotionEvent to normal
        flipXY(ev)
        return toIntercept
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val toHandle = super.onTouchEvent(flipXY(ev))
        // Return MotionEvent to normal
        flipXY(ev)
        return toHandle
    }

    private fun postInitViewPager() {
        try {
            val scroller = ViewPager::class.java.getDeclaredField("mScroller")
            scroller.isAccessible = true
            val interpolator = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true

            mScroller = ScrollerCustomDuration(
                context,
                interpolator.get(null) as Interpolator
            )
            scroller.set(this, mScroller)
        } catch (e: Exception) {
        }

    }

    /**
     * Set the factor by which the duration will change
     */
    fun setScrollDurationFactor(scrollFactor: Double) {
        mScroller!!.setScrollDurationFactor(scrollFactor)
    }

    private fun flipXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val x = ev.y / height * height
        val y = ev.x / width * width
        ev.setLocation(x, y)
        return ev
    }

    private class VerticalPageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            val pageWidth = view.width
            val pageHeight = view.height
            if (position < -1) {
                // This page is way off-screen to the left.
                view.alpha = 0f
            } else if (position <= 1) {
                view.alpha = 1f
                // Counteract the default slide transition
                view.translationX = pageWidth * -position
                // set Y position to swipe in from top
                val yPosition = position * pageHeight
                view.translationY = yPosition
            } else {
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }
    }
}
