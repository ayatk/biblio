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

package com.ayatk.biblio.ui.detail.index.item

import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ItemIndexBinding
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.util.DatePattern
import com.ayatk.biblio.util.format
import com.xwray.groupie.databinding.BindableItem

class IndexItem(
    private val index: Index,
    private val onClickListener: (Index) -> Unit
) : BindableItem<ItemIndexBinding>() {
  override fun getLayout(): Int = R.layout.item_index

  override fun bind(viewBinding: ItemIndexBinding, position: Int) {
    viewBinding.let {
      it.subtitle.text = index.subtitle
      it.publishDate.text = index.lastUpdate.format(DatePattern.YYYY_MM_DD_KK_MM)
      it.indexContainer.setOnClickListener {
        onClickListener(index)
      }
    }
  }
}
