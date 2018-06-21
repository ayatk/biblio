package com.ayatk.biblio.ui.util.customview

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : androidx.viewpager.widget.ViewPager(context, attrs) {

  override fun onInterceptTouchEvent(ev: MotionEvent?) = false

  override fun onTouchEvent(ev: MotionEvent?) = false
}
