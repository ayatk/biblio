/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.repository.novel.NovelTableRepository
import com.ayatk.biblio.view.helper.Navigator
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class NovelTableViewModel
@Inject
constructor(private val navigator: Navigator,
            private val novelTableRepository: NovelTableRepository) : BaseObservable(), ViewModel {

  private val TAG = NovelTableViewModel::class.java.simpleName

  var novelTableViewModels = ObservableArrayList<NovelTableItemViewModel>()

  override fun destroy() {}

  fun start(novel: Novel) {
    novelTableRepository.findAll(novel)
        .map({ novelTables -> convertToViewModel(novelTables) })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            this::renderLibraries,
            { throwable -> Timber.tag(TAG).e(throwable, "Failed to show libraries.") }
        )
  }

  private fun convertToViewModel(novelTables: List<NovelTable>): List<NovelTableItemViewModel> {
    return novelTables.map { novelTable -> NovelTableItemViewModel(navigator, novelTable) }
  }

  private fun renderLibraries(novelTableItemViewModels: List<NovelTableItemViewModel>) {
    if (this.novelTableViewModels.size != novelTableItemViewModels.size) {
      this.novelTableViewModels.clear()
      this.novelTableViewModels.addAll(novelTableItemViewModels)
    }
  }
}
