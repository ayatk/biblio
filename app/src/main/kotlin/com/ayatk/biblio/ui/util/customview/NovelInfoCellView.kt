/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.util.customview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.ayatk.biblio.R

class NovelInfoCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val inflater by lazy {
    LayoutInflater.from(context).inflate(R.layout.view_info_cell, this)
  }

  private val array by lazy {
    context.theme.obtainStyledAttributes(attrs, R.styleable.NovelInfoCellView, 0, 0)
  }

  private val textView by lazy {
    inflater.findViewById<TextView>(R.id.text_view)
  }

  private val iconView by lazy {
    inflater.findViewById<ImageView>(R.id.icon_view)
  }

  var cellText = array.getString(R.styleable.NovelInfoCellView_setCellText)
    set(value) {
      field = value
      textView.text = value
    }

  var cellIcon = array.getResourceId(R.styleable.NovelInfoCellView_setCellIcon, 0)
    set(value) {
      field = value
      iconView.setImageResource(value)
    }

  init {
    textView.text = cellText
    iconView.setImageResource(cellIcon)
  }
}
