package com.ayatk.biblio.ui.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeableViewPager @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

  override fun onInterceptTouchEvent(ev: MotionEvent?) = false

  override fun onTouchEvent(ev: MotionEvent?) = false
}
