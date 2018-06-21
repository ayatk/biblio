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

package com.ayatk.biblio.ui.search.item

import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ItemSearchResultBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.DatePattern
import com.ayatk.biblio.util.ext.setVisible
import com.ayatk.biblio.util.format
import com.xwray.groupie.databinding.BindableItem

class SearchResultItem(
  private val novel: Map.Entry<Novel, Boolean>,
  private val onClickListener: (Novel) -> Unit,
  private val onDownloadClickListener: (Novel) -> Unit
) : BindableItem<ItemSearchResultBinding>() {

  override fun getLayout(): Int = R.layout.item_search_result

  override fun getId(): Long = novel.key.code.toByteArray().joinToString("").toLong()

  override fun bind(viewBinding: ItemSearchResultBinding, position: Int) {
    viewBinding.let {
      it.novel = novel.key
      it.isDownloaded = novel.value
      it.searchResult.setOnClickListener {
        onClickListener(novel.key)
      }
      it.lastUpdate.text = novel.key.lastUpload.format(DatePattern.YYYY_MM_DD_KK_MM)
      it.readProgress.setVisible(novel.key.novelState != NovelState.SHORT_STORY)
      it.addLibrary.setOnClickListener {
        onDownloadClickListener(novel.key)
      }
    }
  }
}
