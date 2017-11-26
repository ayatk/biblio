/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.detail.table

import android.databinding.BaseObservable
import android.view.View
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.DateFormat

class NovelTableItemViewModel(
    private val navigator: Navigator,
    val novelTable: NovelTable
) : BaseObservable(), ViewModel {

  val lastUpdate: String =
      if (novelTable.publishDate == null) ""
      else DateFormat.yyyyMMddkkmm.format(novelTable.publishDate)

  override fun destroy() {}

  fun onItemClick(@Suppress("UNUSED_PARAMETER") view: View) {
    if (!novelTable.isChapter) {
      navigator.navigateToNovelBody(
          novelTable.novel,
          requireNotNull(novelTable.page, { "ページ番号がはいってないぞい" })
      )
    }
  }
}
