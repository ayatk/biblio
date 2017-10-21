/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.view.View
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm
import com.ayatk.biblio.view.helper.Navigator

class NovelTableItemViewModel(
    private val navigator: Navigator,
    val novelTable: NovelTable) : BaseObservable(), ViewModel {

  val lastUpdate: String =
      if (novelTable.publishDate == null) ""
      else FORMAT_yyyyMMdd_kkmm.format(novelTable.publishDate)

  override fun destroy() {}

  fun onItemClick(@Suppress("UNUSED_PARAMETER") view: View) {
    if (!novelTable.isChapter) {
      navigator.navigateToNovelBody(novelTable.novel,
          requireNotNull(novelTable.page, { "ページ番号がはいってないぞい" }))
    }
  }
}
