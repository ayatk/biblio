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
import com.ayatk.biblio.view.helper.Navigator
import java.text.SimpleDateFormat
import java.util.Locale

class LibraryViewModel
constructor(private val navigator: Navigator,
            private val context: Context,
            val library: Library) : BaseObservable(), ViewModel {

  private val dataFormat = SimpleDateFormat("yyyy/MM/dd kk:mm", Locale.getDefault())

  val lastUpdate: String = dataFormat.format(library.novel.lastUpdateDate)

  val isShortStory = library.novel.novelState == NovelState.SHORT_STORY

  val novelProgress: String
      = context.getString(R.string.novel_progress, 0, library.novel.totalPages)

  val isShowTag: Boolean = DefaultPrefs.get(context).showTagAtLibrary

  override fun destroy() {}

  fun onItemClick(@Suppress("UNUSED_PARAMETER") view: View) {
    navigator.navigateToNovelDetail(library.novel)
  }
}
