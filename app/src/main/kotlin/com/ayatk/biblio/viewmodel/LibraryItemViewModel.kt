/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.view.View
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.pref.DefaultPrefs
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm
import com.ayatk.biblio.ui.util.helper.Navigator

class LibraryItemViewModel
constructor(private val navigator: Navigator,
            val library: Library,
            defaultPrefs: DefaultPrefs) : BaseObservable(), ViewModel {

  val lastUpdate: String = FORMAT_yyyyMMdd_kkmm.format(library.novel.lastUpdateDate)

  val isShortStory = library.novel.novelState == NovelState.SHORT_STORY

  val isShowTag: Boolean = defaultPrefs.showTagAtLibrary

  override fun destroy() {}

  fun onItemClick(@Suppress("UNUSED_PARAMETER") view: View) {
    navigator.navigateToNovelDetail(library.novel)
  }
}
