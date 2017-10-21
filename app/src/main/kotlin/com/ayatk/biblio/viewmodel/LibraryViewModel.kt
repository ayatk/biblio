/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.view.View
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.pref.DefaultPrefsWrapper
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.repository.novel.NovelRepository
import com.ayatk.biblio.repository.novel.NovelTableRepository
import com.ayatk.biblio.view.helper.Navigator
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel
@Inject constructor(
    private val navigator: Navigator,
    private val libraryRepository: LibraryRepository,
    private val novelRepository: NovelRepository,
    private val novelTableRepository: NovelTableRepository,
    private val defaultPrefsWrapper: DefaultPrefsWrapper) : BaseObservable(), ViewModel {

  private val TAG = LibraryItemViewModel::class.java.simpleName

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
      LibraryItemViewModel(navigator, library, defaultPrefsWrapper.prefs)
    }
  }

  fun start(refresh: Boolean) {
    if (refresh) {
      novelRepository.isDirty = true
      novelTableRepository.isDirty = true
    }

    loadLibraries()
        .map({ library -> convertToViewModel(library) })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { viewModel ->
              if (refresh) {
                Publisher.values().map { pub ->
                  novelRepository.findAll(viewModel
                      .filter { it.library.novel.publisher == pub }
                      .map { it.library.novel.code }, pub)
                      .subscribe()
                }
                viewModel.toObservable()
                    .concatMap { novelTableRepository.findAll(it.library.novel).toObservable() }
                    .subscribe({
                      refreshing = false
                      novelRepository.isDirty = false
                      novelTableRepository.isDirty = false
                    })
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
