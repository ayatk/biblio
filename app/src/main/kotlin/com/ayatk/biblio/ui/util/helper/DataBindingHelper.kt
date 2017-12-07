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

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ayatk.biblio.R
import com.ayatk.biblio.model.Ranking
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

  @JvmStatic
  @BindingAdapter("addRankingItems")
  fun LinearLayout.addRankingItems(rankings: List<Ranking>) {
    this.removeAllViews()
    val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    rankings.map {
      val rankingItem = inflater.inflate(R.layout.view_ranking_top_item, null)
      val rank = rankingItem.findViewById<ImageView>(R.id.rank)
      val rankText = rankingItem.findViewById<TextView>(R.id.rank_text)
      // ランキングのイメージ
      if (it.rank <= 3) {
        rankText.visibility = View.GONE
      } else {
        rankText.text = it.rank.toString()
        rank.setImageResource(android.R.color.transparent)
      }

      val title = rankingItem.findViewById<TextView>(R.id.novel_title)
      title.text = it.novel.title
      val author = rankingItem.findViewById<TextView>(R.id.novel_author)
      author.text = it.novel.writer
      this.addView(rankingItem)
    }
  }
}
