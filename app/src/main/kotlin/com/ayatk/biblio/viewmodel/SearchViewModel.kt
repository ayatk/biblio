/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import com.ayatk.biblio.data.narou.NarouClient
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(private val narouClient: NarouClient) : ViewModel {

  private val compositeDisposable = CompositeDisposable()

  override fun destroy() {
    compositeDisposable.clear()
  }

  fun search(query: String) {
    val formattedQuery = query.trim { it <= ' ' }
    if (formattedQuery.isNotBlank()) {
    }
  }
}
