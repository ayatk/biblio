/*
 * Copyright (c) 2016-2018 ayatk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ayatk.biblio.ui.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ayatk.biblio.R

class InfoCellView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val inflater by lazy {
    LayoutInflater.from(context).inflate(R.layout.view_info_cell, this)
  }

  private val array by lazy {
    context.theme.obtainStyledAttributes(attrs, R.styleable.InfoCellView, 0, 0)
  }

  private val textView by lazy {
    inflater.findViewById<TextView>(R.id.text_view)
  }

  private val iconView by lazy {
    inflater.findViewById<ImageView>(R.id.icon_view)
  }

  var cellText = array.getString(R.styleable.InfoCellView_setCellText)
    set(value) {
      field = value
      textView.text = value
    }

  var cellIcon = array.getResourceId(R.styleable.InfoCellView_setCellIcon, 0)
    set(value) {
      field = value
      iconView.setImageResource(value)
    }

  init {
    textView.text = cellText
    iconView.setImageResource(cellIcon)
  }
}
