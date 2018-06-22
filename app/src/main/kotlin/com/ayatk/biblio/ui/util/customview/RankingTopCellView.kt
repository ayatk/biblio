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
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.getSystemService
import com.ayatk.biblio.R
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.RankingType
import com.ayatk.biblio.ui.ranking.RankingActivity
import com.ayatk.biblio.ui.util.helper.DataBindingHelper.rankingIcon
import com.ayatk.biblio.ui.util.helper.DataBindingHelper.rankingText

class RankingTopCellView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

  private val rankingList: ArrayList<Ranking> = ArrayList()

  private val container: LinearLayout
  private val titleView: TextView
  private val readMore: Button
  lateinit var rankingType: RankingType

  private val array by lazy {
    context.theme.obtainStyledAttributes(attrs, R.styleable.RankingTopCellView, 0, 0)
  }

  private var title = array.getString(R.styleable.RankingTopCellView_setRankingTitle)
    set(value) {
      field = value
      titleView.text = value
    }

  init {
    LayoutInflater.from(context).inflate(R.layout.view_ranking_top_cell, this)
    container = findViewById(R.id.ranking_container)
    titleView = findViewById(R.id.ranking_title)
    readMore = findViewById(R.id.read_more)
    titleView.text = title
    readMore.setOnClickListener {
      context.startActivity(RankingActivity.createIntent(context, rankingType))
    }
  }

  fun setRankings(rankings: List<Ranking>?) {
    rankings ?: return
    if (rankingList == rankings) return
    rankingList.clear()
    rankingList.addAll(rankings)

    updateRankings()
  }

  private fun updateRankings() {
    container.removeAllViews()
    val inflater = this.context.getSystemService<LayoutInflater>()!!
    rankingList.map {
      val rankingItem = inflater.inflate(R.layout.item_ranking_top, null)
      val rank = rankingItem.findViewById<ImageView>(R.id.rank)
      val rankText = rankingItem.findViewById<TextView>(R.id.rank_text)

      rank.rankingIcon(it)
      rankText.rankingText(it)

      val title = rankingItem.findViewById<TextView>(R.id.novel_title)
      val author = rankingItem.findViewById<TextView>(R.id.novel_author)
      if (it.novel.title.isEmpty()) {
        title.text = "この小説は見ることができません"
        author.text = ""
      } else {
        title.text = it.novel.title
        author.text = it.novel.writer
      }

      container.addView(rankingItem)
    }
  }
}
