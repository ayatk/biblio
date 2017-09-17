/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm


class SearchResultItemViewModel(val novel: Novel) : BaseObservable(), ViewModel {

  val lastUpdate: String = FORMAT_yyyyMMdd_kkmm.format(novel.lastUpdateDate)

  val isShortStory = novel.novelState == NovelState.SHORT_STORY

  val novelProgress: String = "全${novel.totalPages}部"


  override fun destroy() {
  }
}
