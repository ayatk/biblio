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

package com.ayatk.biblio.ui.home.library.item

import com.ayatk.biblio.R
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.databinding.ItemLibraryBinding
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.DateFormat
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.databinding.BindableItem

class LibraryItem(
    private val library: Library,
    private val defaultPrefs: DefaultPrefs,
    private val onClickListener: (Novel) -> Unit
) : BindableItem<ItemLibraryBinding>() {

  override fun getLayout(): Int = R.layout.item_library

  override fun bind(viewBinding: ItemLibraryBinding, position: Int) {
    viewBinding.let {
      it.library = library
      it.libraryItem.setOnClickListener {
        onClickListener(library.novel)
      }
      it.lastUpdate.text = DateFormat.yyyyMMddkkmm.format(library.novel.lastUpdateDate)
      it.readProgress.setVisible(library.novel.novelState != NovelState.SHORT_STORY)
      it.tagLayout.setVisible(defaultPrefs.showTagAtLibrary && library.tag.isNotEmpty())
    }
  }
}
