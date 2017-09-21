/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.ObservableArrayList
import android.view.View
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.model.Novel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class SearchViewModel(private val narouClient: NarouClient) : ViewModel {

  private val compositeDisposable = CompositeDisposable()

  val searchResult = ObservableArrayList<SearchResultItemViewModel>()

  val searchResultVisibility: BehaviorSubject<Int> = BehaviorSubject.createDefault(View.GONE)

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
      compositeDisposable.add(narouClient.getNovel(builtQuery).subscribeOn(Schedulers.io())
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
    return novels.map { novel -> SearchResultItemViewModel(novel) }
  }
}
