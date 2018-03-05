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
import com.ayatk.biblio.databinding.ViewSearchResultItemBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.DateFormat
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.databinding.BindableItem
import io.reactivex.Completable

class SearchResultItem(
    private val novel: Map.Entry<Novel, Boolean>,
    private val onClickListener: (Novel) -> Unit,
    private val onDownloadClickListener: (Novel) -> Completable
) : BindableItem<ViewSearchResultItemBinding>() {

  var isDownloaded = false

  override fun getLayout(): Int = R.layout.view_search_result_item

  override fun bind(viewBinding: ViewSearchResultItemBinding, position: Int) {
    viewBinding.let {
      it.novel = novel.key
      it.isDownloaded = isDownloaded
      it.searchResult.setOnClickListener {
        onClickListener(novel.key)
      }
      it.lastUpdate.text = DateFormat.yyyyMMddkkmm.format(novel.key.lastUpload)
      it.readProgress.setVisible(novel.key.novelState != NovelState.SHORT_STORY)
      it.addLibrary.setOnClickListener {
        onDownloadClickListener(novel.key)
            .subscribe {
              isDownloaded = true
            }
      }
    }
  }
}