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

package com.ayatk.biblio.ui.detail.table

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import com.ayatk.biblio.data.datasource.novel.IndexDataSource
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class IndexViewModel @Inject constructor(
    private val indexDataSource: IndexDataSource,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  var indexViewModels = ObservableArrayList<IndexItemViewModel>()

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  fun start(novel: Novel) {
    indexDataSource.findAll(novel)
        .map({ indexes -> convertToViewModel(indexes) })
        .observeOn(schedulerProvider.ui())
        .subscribe(
            this::renderLibraries,
            { throwable -> Timber.e(throwable, "Failed to show libraries.") }
        )
        .addTo(compositeDisposable)
  }

  private fun convertToViewModel(indices: List<Index>): List<IndexItemViewModel> {
    return indices.map { novelTable ->
      IndexItemViewModel(novelTable)
    }
  }

  private fun renderLibraries(indexItemViewModels: List<IndexItemViewModel>) {
    if (this.indexViewModels.size != indexItemViewModels.size) {
      this.indexViewModels.clear()
      this.indexViewModels.addAll(indexItemViewModels)
    }
  }
}
