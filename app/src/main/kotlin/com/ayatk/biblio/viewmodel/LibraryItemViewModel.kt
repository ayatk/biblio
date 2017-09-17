/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.view.View
import com.ayatk.biblio.R
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.pref.DefaultPrefs
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm
import com.ayatk.biblio.view.helper.Navigator

class LibraryItemViewModel
constructor(private val navigator: Navigator,
            val library: Library,
            context: Context) : BaseObservable(), ViewModel {

  val lastUpdate: String = FORMAT_yyyyMMdd_kkmm.format(library.novel.lastUpdateDate)

  val isShortStory = library.novel.novelState == NovelState.SHORT_STORY

  val isShowTag: Boolean = DefaultPrefs.get(context).showTagAtLibrary

  override fun destroy() {}

  fun onItemClick(@Suppress("UNUSED_PARAMETER") view: View) {
    navigator.navigateToNovelDetail(library.novel)
  }
}
