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

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.view.View
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.data.datasource.novel.NovelDataSource
import com.ayatk.biblio.data.datasource.novel.NovelTableDataSource
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.toObservable
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val novelDataSource: NovelDataSource,
    private val novelTableDataSource: NovelTableDataSource,
    private val defaultPrefs: DefaultPrefs,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  var libraryViewModels = ObservableArrayList<LibraryItemViewModel>()
  var emptyViewVisibility = MutableLiveData<Int>()
  var recyclerViewVisibility = MutableLiveData<Int>()
  var refreshing = MutableLiveData<Boolean>()

  override fun onCleared() {
    compositeDisposable.clear()
  }

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
      novelDataSource.isDirty = true
      novelTableDataSource.isDirty = true
    }

    loadLibraries()
        .map({ library -> convertToViewModel(library) })
        .observeOn(schedulerProvider.ui())
        .subscribe(
            { viewModel ->
              if (refresh) {
                Publisher.values().map { pub ->
                  novelDataSource.findAll(
                      viewModel
                          .filter { it.library.novel.publisher == pub }
                          .map { it.library.novel.code }, pub
                  )
                      .subscribe()
                }
                viewModel.toObservable()
                    .concatMap { novelTableDataSource.findAll(it.library.novel).toObservable() }
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        {
                          refreshing.postValue(false)
                          novelDataSource.isDirty = false
                          novelTableDataSource.isDirty = false
                        }
                    )
                    .addTo(compositeDisposable)
              }
              renderLibraries(viewModel)
            },
            { throwable -> Timber.e(throwable, "Failed to show libraries.") }
        )
        .addTo(compositeDisposable)
  }

  private fun renderLibraries(libraryViewModels: List<LibraryItemViewModel>) {
    if (this.libraryViewModels != libraryViewModels) {
      this.libraryViewModels.clear()
      this.libraryViewModels.addAll(libraryViewModels)
    }
    this.emptyViewVisibility.postValue(if (this.libraryViewModels.size > 0) View.GONE else View.VISIBLE)
    this.recyclerViewVisibility.postValue(if (this.libraryViewModels.size > 0) View.VISIBLE else View.GONE)
  }
}
