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

class NovelTablesViewModel
@Inject
constructor(private val navigator: Navigator,
            private val novelTableRepository: NovelTableRepository) : BaseObservable(), ViewModel {

  private val TAG = NovelTablesViewModel::class.java.simpleName

  var novelTableViewModels = ObservableArrayList<NovelTableViewModel>()

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

  private fun convertToViewModel(novelTables: List<NovelTable>): List<NovelTableViewModel> {
    return novelTables.map { novelTable -> NovelTableViewModel(navigator, novelTable) }
  }

  private fun renderLibraries(novelTableViewModels: List<NovelTableViewModel>) {
    if (this.novelTableViewModels.size != novelTableViewModels.size) {
      this.novelTableViewModels.clear()
      this.novelTableViewModels.addAll(novelTableViewModels.filter { !it.novelTable.isChapter })
    }
  }
}
