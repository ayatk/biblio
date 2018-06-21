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

package com.ayatk.biblio.ui.ranking.item

import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ItemRankingListBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.databinding.BindableItem

class RankingItem(
  private val ranking: Ranking,
  private val onClickListener: (Novel) -> Unit
) : BindableItem<ItemRankingListBinding>(ranking.rank.toLong()) {

  override fun getLayout(): Int = R.layout.item_ranking_list

  override fun getId(): Long = ranking.novel.code.toByteArray().joinToString("").toLong()

  override fun bind(viewBinding: ItemRankingListBinding, position: Int) {
    viewBinding.ranking = ranking
    viewBinding.rankingItem.setOnClickListener {
      onClickListener(ranking.novel)
    }
    viewBinding.readProgress.setVisible(ranking.novel.novelState != NovelState.SHORT_STORY)
  }
}
