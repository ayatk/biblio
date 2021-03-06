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

package com.ayatk.biblio.ui.util.helper

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.ayatk.biblio.R
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.util.ext.color
import com.google.android.flexbox.FlexboxLayout

object DataBindingHelper {

  private const val GOLD_RANK = 1
  private const val SILVER_RANK = 2
  private const val BRONZE_RANK = 3

  @JvmStatic
  @BindingConversion
  fun convertColorToDrawable(color: Int): ColorDrawable = ColorDrawable(color)

  @JvmStatic
  @BindingAdapter("tags")
  fun FlexboxLayout.setTags(tags: List<String>?) {
    if (tags == null) {
      return
    }
    this.removeAllViews()
    val inflater = this.context.getSystemService<LayoutInflater>()!!
    tags.map {
      val tagItem = inflater.inflate(R.layout.view_tag, null)
      val frameLayout = tagItem.findViewById<FrameLayout>(R.id.tag_container)
      val textView = tagItem.findViewById<TextView>(R.id.tag)
      textView.text = it
      this.addView(frameLayout)
    }
  }

  @JvmStatic
  @BindingAdapter("rankingIcon")
  fun ImageView.rankingIcon(ranking: Ranking) {
    // ランキングのイメージ
    setImageResource(R.drawable.ic_crown_24)
    when (ranking.rank) {
      GOLD_RANK -> this.setColorFilter(context.color(R.color.gold))
      SILVER_RANK -> this.setColorFilter(context.color(R.color.silver))
      BRONZE_RANK -> this.setColorFilter(context.color(R.color.bronze))
      else -> this.setImageResource(android.R.color.transparent)
    }
  }

  @JvmStatic
  @BindingAdapter("rankingText")
  fun TextView.rankingText(ranking: Ranking) {
    this.text = ranking.rank.toString()
    this.visibility = View.VISIBLE
    when (ranking.rank) {
      GOLD_RANK, SILVER_RANK, BRONZE_RANK -> this.visibility = View.GONE
    }
  }
}
