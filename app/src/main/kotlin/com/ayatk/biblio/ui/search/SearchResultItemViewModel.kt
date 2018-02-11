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
import com.ayatk.biblio.util.DateFormat
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SearchResultItemViewModel(
    libraries: List<Library>,
    val novel: Novel,
    private val libraryRepository: LibraryRepository
) : BaseObservable(), ViewModel {

  @Bindable
  var downloadVisibility: Int = View.VISIBLE

  @Bindable
  var downloadedVisibility: Int = View.GONE

  val lastUpdate: String = DateFormat.yyyyMMddkkmm.format(novel.lastUpdateDate)

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
