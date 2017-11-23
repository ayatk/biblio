/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.search

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SearchResultItemViewModel(
    libraries: List<Library>,
    val novel: Novel,
    val libraryRepository: LibraryRepository) : BaseObservable(), ViewModel {

  @Bindable
  var downloadVisibility: Int = View.VISIBLE

  @Bindable
  var downloadedVisibility: Int = View.GONE

  val lastUpdate: String = FORMAT_yyyyMMdd_kkmm.format(novel.lastUpdateDate)

  val isShortStory = novel.novelState == NovelState.SHORT_STORY

  init {
    if (novel in libraries.map { library -> library.novel }) {
      downloadVisibility = View.GONE
      notifyPropertyChanged(BR.downloadVisibility)
      downloadedVisibility = View.VISIBLE
      notifyPropertyChanged(BR.downloadedVisibility)
    }
  }

  fun View.onClickAddLibrary() {
    libraryRepository.save(Library(novel = novel, tag = listOf()))
        .doOnSubscribe {
          downloadVisibility = View.GONE
          notifyPropertyChanged(BR.downloadVisibility)
        }
        .subscribeBy(
            onComplete = {
              downloadedVisibility = View.VISIBLE
              notifyPropertyChanged(BR.downloadedVisibility)
            },
            onError = { throwable ->
              Timber.tag("SearchResultItemViewModel").e(throwable, "Failed to save libraries.")
            }
        )
  }

  override fun destroy() {
  }
}
