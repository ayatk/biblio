/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.view.View
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.view.helper.Navigator
import java.text.SimpleDateFormat
import java.util.Locale

class NovelTableViewModel
constructor(private val navigator: Navigator,
            val novelTable: NovelTable) : BaseObservable(), ViewModel {

  private val DATA_FORMAT = SimpleDateFormat("yyyy/MM/dd kk:mm", Locale.getDefault())

  val lastUpdate: String =
      if (novelTable.publishDate == null) ""
      else DATA_FORMAT.format(novelTable.publishDate)

  override fun destroy() {}

  fun onItemClick(@Suppress("UNUSED_PARAMETER") view: View) {
    navigator.navigateToNovelBody(novelTable.novel, novelTable.page!!)
  }
}
