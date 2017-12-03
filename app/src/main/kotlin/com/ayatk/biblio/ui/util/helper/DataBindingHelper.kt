/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.util.helper

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.ayatk.biblio.R
import com.google.android.flexbox.FlexboxLayout

object DataBindingHelper {

  @JvmStatic
  @BindingConversion
  fun convertColorToDrawable(color: Int): ColorDrawable = ColorDrawable(color)

  @JvmStatic
  @BindingAdapter("tags")
  fun FlexboxLayout.setTags(tags: List<String>) {
    this.removeAllViews()
    val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    tags.map {
      val tagItem = inflater.inflate(R.layout.view_tag, null)
      val frameLayout = tagItem.findViewById<FrameLayout>(R.id.tag_container)
      val textView = tagItem.findViewById<TextView>(R.id.tag)
      textView.text = it
      this.addView(frameLayout)
    }
  }
}
