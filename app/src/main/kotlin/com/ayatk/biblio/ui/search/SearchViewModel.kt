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

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.repository.library.LibraryDataSource
import com.ayatk.biblio.ui.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val narouClient: NarouClient,
    private val libraryDataSource: LibraryDataSource
) : ViewModel {

  init {
    libraryDataSource.findAll()
        .subscribeOn(Schedulers.io())
        .subscribe(
            { libraries -> this.libraries = libraries },
            { t -> Log.e("SearchViewModel", t.toString()) }
        )
  }

  private val compositeDisposable = CompositeDisposable()

  val searchResult = ObservableArrayList<SearchResultItemViewModel>()

  val searchResultVisibility: BehaviorSubject<Int> = BehaviorSubject.createDefault(View.GONE)

  var libraries = listOf<Library>()

  override fun destroy() {
    searchResultVisibility.onComplete()
    compositeDisposable.clear()
  }

  fun search(query: String) {
    val formattedQuery = query.trim { it <= ' ' }
    if (formattedQuery.isNotBlank()) {
      searchResultVisibility.onNext(View.VISIBLE)
      val builtQuery = QueryBuilder().searchWords(query).size(100).build()
      compositeDisposable.clear()
      compositeDisposable.add(
          narouClient.getNovel(builtQuery)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .map({ novels -> convertToViewModel(novels) })
              .subscribe(
                  { viewModels ->
                    if (viewModels.isNotEmpty()) {
                      searchResult.clear()
                      searchResult.addAll(viewModels)
                    }
                  },
                  { _ -> /* TODO: あとで頑張る */ }
              )
      )
    } else {
      searchResultVisibility.onNext(View.GONE)
    }
  }

  private fun convertToViewModel(novels: List<Novel>): List<SearchResultItemViewModel> {
    return novels.map { novel ->
      SearchResultItemViewModel(libraries, novel, libraryDataSource)
    }
  }
}
