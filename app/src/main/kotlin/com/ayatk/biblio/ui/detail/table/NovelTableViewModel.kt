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

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.ayatk.biblio.data.datasource.novel.NovelTableDataSource
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class NovelTableViewModel @Inject constructor(
    private val novelTableDataSource: NovelTableDataSource,
    private val schedulerProvider: SchedulerProvider
) : BaseObservable(), ViewModel {

  private val compositeDisposable = CompositeDisposable()

  var novelTableViewModels = ObservableArrayList<NovelTableItemViewModel>()

  override fun destroy() {
    compositeDisposable.clear()
  }

  fun start(novel: Novel) {
    novelTableDataSource.findAll(novel)
        .map({ novelTables -> convertToViewModel(novelTables) })
        .observeOn(schedulerProvider.ui())
        .subscribe(
            this::renderLibraries,
            { throwable -> Timber.e(throwable, "Failed to show libraries.") }
        )
        .addTo(compositeDisposable)
  }

  private fun convertToViewModel(novelTables: List<NovelTable>): List<NovelTableItemViewModel> {
    return novelTables.map { novelTable ->
      NovelTableItemViewModel(novelTable)
    }
  }

  private fun renderLibraries(novelTableItemViewModels: List<NovelTableItemViewModel>) {
    if (this.novelTableViewModels.size != novelTableItemViewModels.size) {
      this.novelTableViewModels.clear()
      this.novelTableViewModels.addAll(novelTableItemViewModels)
    }
  }
}
