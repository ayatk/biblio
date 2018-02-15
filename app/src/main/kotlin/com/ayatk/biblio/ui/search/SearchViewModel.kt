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

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val narouClient: NarouClient,
    private val libraryRepository: LibraryRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

  private val compositeDisposable = CompositeDisposable()

  val searchResult = ObservableArrayList<SearchResultItemViewModel>()

  val searchResultVisibility = MutableLiveData<Int>()

  var libraries = listOf<Library>()

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() {
    libraryRepository.findAll()
        .observeOn(schedulerProvider.ui())
        .subscribe(
            { libraries -> this.libraries = libraries },
            { t -> Log.e("SearchViewModel", t.toString()) }
        )
        .addTo(compositeDisposable)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  fun search(query: String) {
    val formattedQuery = query.trim { it <= ' ' }
    if (formattedQuery.isNotBlank()) {
      searchResultVisibility.postValue(View.VISIBLE)
      val builtQuery = QueryBuilder().searchWords(query).size(100).build()
      compositeDisposable.clear()
      narouClient.getNovel(builtQuery)
          .map({ novels -> convertToViewModel(novels) })
          .subscribeOn(schedulerProvider.io())
          .observeOn(schedulerProvider.ui())
          .subscribe(
              { viewModels ->
                if (viewModels.isNotEmpty()) {
                  searchResult.clear()
                  searchResult.addAll(viewModels)
                }
              },
              Timber::e
          )
          .addTo(compositeDisposable)
    } else {
      searchResultVisibility.postValue(View.GONE)
    }
  }

  private fun convertToViewModel(novels: List<Novel>): List<SearchResultItemViewModel> {
    return novels.map { novel ->
      SearchResultItemViewModel(libraries, novel, libraryRepository)
    }
  }
}
