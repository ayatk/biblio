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

package com.ayatk.biblio.ui.home.library

import android.content.Context
import android.databinding.BaseObservable
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.DateFormat

class LibraryItemViewModel(
    val library: Library,
    defaultPrefs: DefaultPrefs
) : BaseObservable(), ViewModel {

  val lastUpdate: String = DateFormat.yyyyMMddkkmm.format(library.novel.lastUpdateDate)

  val isShortStory = library.novel.novelState == NovelState.SHORT_STORY

  val isShowTag: Boolean = defaultPrefs.showTagAtLibrary

  override fun destroy() {}

  fun onItemClick(context: Context) {
    Navigator.navigateToNovelDetail(context, library.novel)
  }
}
