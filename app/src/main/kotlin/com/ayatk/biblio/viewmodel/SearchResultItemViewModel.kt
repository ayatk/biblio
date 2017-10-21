/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.view.View
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm
import timber.log.Timber


class SearchResultItemViewModel(
    val novel: Novel,
    val libraryRepository: LibraryRepository) : BaseObservable(), ViewModel {

  val lastUpdate: String = FORMAT_yyyyMMdd_kkmm.format(novel.lastUpdateDate)

  val isShortStory = novel.novelState == NovelState.SHORT_STORY

  fun View.onClickAddLibrary() {
    libraryRepository.save(Library(novel = novel, tag = listOf())).subscribe(
        { },
        { throwable ->
          Timber.tag("SearchResultItemViewModel").e(throwable, "Failed to save libraries.")
        }
    )
  }

  override fun destroy() {
  }
}
