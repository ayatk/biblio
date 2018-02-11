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

package com.ayatk.biblio.ui.home.library

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.view.View
import com.ayatk.biblio.BR
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.repository.novel.NovelDataRepository
import com.ayatk.biblio.repository.novel.NovelTableDataRepository
import com.ayatk.biblio.ui.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val novelDataRepository: NovelDataRepository,
    private val novelTableDataRepository: NovelTableDataRepository,
    private val defaultPrefs: DefaultPrefs
) : BaseObservable(), ViewModel {

  companion object {
    private val TAG = LibraryItemViewModel::class.java.simpleName
  }

  var libraryViewModels = ObservableArrayList<LibraryItemViewModel>()

  @Bindable
  var emptyViewVisibility: Int = View.GONE

  @Bindable
  var recyclerViewVisibility: Int = View.VISIBLE

  @Bindable
  var refreshing: Boolean = false
    set(value) {
      field = value
      notifyPropertyChanged(BR.refreshing)
    }

  override fun destroy() {}

  fun onSwipeRefresh() {
    start(true)
  }

  private fun loadLibraries(): Single<List<Library>> {
    return libraryRepository.findAll()
        // 最終更新日時順でソート
        .map({ libraries -> libraries.sortedByDescending { (_, novel) -> novel.lastUpdateDate } })
  }

  private fun convertToViewModel(libraries: List<Library>): List<LibraryItemViewModel> {
    return libraries.map { library ->
      LibraryItemViewModel(library, defaultPrefs)
    }
  }

  fun start(refresh: Boolean) {
    if (refresh) {
      novelDataRepository.isDirty = true
      novelTableDataRepository.isDirty = true
    }

    loadLibraries()
        .map({ library -> convertToViewModel(library) })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { viewModel ->
              if (refresh) {
                Publisher.values().map { pub ->
                  novelDataRepository.findAll(
                      viewModel
                          .filter { it.library.novel.publisher == pub }
                          .map { it.library.novel.code }, pub
                  )
                      .subscribe()
                }
                viewModel.toObservable()
                    .concatMap { novelTableDataRepository.findAll(it.library.novel).toObservable() }
                    .subscribe(
                        {
                          refreshing = false
                          novelDataRepository.isDirty = false
                          novelTableDataRepository.isDirty = false
                        }
                    )
              }
              renderLibraries(viewModel)
            },
            { throwable -> Timber.tag(TAG).e(throwable, "Failed to show libraries.") }
        )
  }

  private fun renderLibraries(libraryViewModels: List<LibraryItemViewModel>) {
    if (this.libraryViewModels != libraryViewModels) {
      this.libraryViewModels.clear()
      this.libraryViewModels.addAll(libraryViewModels)
    }
    this.emptyViewVisibility = if (this.libraryViewModels.size > 0) View.GONE else View.VISIBLE
    this.recyclerViewVisibility = if (this.libraryViewModels.size > 0) View.VISIBLE else View.GONE
    notifyPropertyChanged(BR.emptyViewVisibility)
    notifyPropertyChanged(BR.recyclerViewVisibility)
  }
}
