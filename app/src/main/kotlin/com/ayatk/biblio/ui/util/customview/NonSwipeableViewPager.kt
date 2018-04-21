package com.ayatk.biblio.ui.util.customview

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

  override fun onInterceptTouchEvent(ev: MotionEvent?) = false

  override fun onTouchEvent(ev: MotionEvent?) = false
}
