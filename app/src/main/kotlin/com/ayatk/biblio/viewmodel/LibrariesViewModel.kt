/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.repository.novel.NovelRepository
import com.ayatk.biblio.repository.novel.NovelTableRepository
import com.ayatk.biblio.view.helper.Navigator
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import timber.log.Timber
import javax.inject.Inject

class LibrariesViewModel
@Inject constructor(private val navigator: Navigator,
                    private val libraryRepository: LibraryRepository,
                    private val novelRepository: NovelRepository,
                    private val novelTableRepository: NovelTableRepository,
                    connectivityManager: ConnectivityManager) : BaseObservable(), ViewModel {

  private val TAG = LibraryViewModel::class.java.simpleName

  var libraryViewModels = ObservableArrayList<LibraryViewModel>()

  @Bindable
  var emptyViewVisibility: Int = View.GONE

  @Bindable
  var recyclerViewVisibility: Int = View.VISIBLE

  @Bindable
  var refreshing: Boolean = false
    set(value) = notifyPropertyChanged(BR.refreshing)

  private val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

  override fun destroy() {}

  fun onSwipeRefresh(context: Context) {
    start(context, true)
  }

  private fun loadLibraries(): Single<List<Library>> {
    return libraryRepository.findAll()
        // 最終更新日時順でソート
        .map({ libraries -> libraries.sortedByDescending { (_, novel) -> novel.lastUpdateDate } })
  }

  private fun convertToViewModel(
      context: Context, libraries: List<Library>): List<LibraryViewModel> {
    return libraries.map { library -> LibraryViewModel(navigator, context, library) }
  }

  fun start(context: Context, refresh: Boolean) {
    if (refresh) {
      novelRepository.isDirty = true
      novelTableRepository.isDirty = true
    }

    loadLibraries()
        .map({ library -> convertToViewModel(context, library) })
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
                      refreshing = true
                      novelRepository.isDirty = false
                      novelTableRepository.isDirty = false
                    })
              }
              renderLibraries(viewModel)
            },
            { throwable -> Timber.tag(TAG).e(throwable, "Failed to show libraries.") }
        )
  }

  fun getNovels(context: Context, ncodeURL: String) {
    if (ncodeURL.isEmpty()) {
      // Do nothing
      return
    }

    if (networkInfo == null || !networkInfo.isConnected) {
      Toast.makeText(context, "ダウンロードできませんでした\nインターネットにつながっていません", Toast.LENGTH_LONG).show()
      return
    }

    val urls = ncodeURL.split("\n")

    val regex = Regex("""^https?://(ncode|novel18)\.syosetu\.com/(n\d{4}[a-z]{1,2})/?""")
    val unUsedUrlSize = urls.filter { !regex.matches(it) }.size
    val dounloadUrls = urls.filter { regex.matches(it) }

    if (unUsedUrlSize != 0) {
      Toast.makeText(context, "${unUsedUrlSize}個の小説がダウンロードできませんでした。", Toast.LENGTH_LONG).show()
    }



    dounloadUrls.map {
      val publisher = if (regex.matchEntire(it)!!.destructured.component1() == "ncode")
        Publisher.NAROU else Publisher.NOCTURNE_MOONLIGHT

      val ncode = regex.matchEntire(it)!!.destructured.component2()
      novelRepository.isDirty = true
      novelRepository.find(ncode, publisher)
          .subscribe(
              { novel ->
                saveLibrary(context, novel)
                novelRepository.isDirty = false
              },
              { throwable -> Timber.tag(TAG).e(throwable, "Failed to get Novels.") }
          )
    }
  }

  private fun saveLibrary(context: Context, novel: Novel) {
    libraryRepository.save(Library(novel = novel, tag = listOf())).subscribe(
        { start(context, false) },
        { throwable -> Timber.tag(TAG).e(throwable, "Failed to save libraries.") }
    )
  }

  private fun renderLibraries(libraryViewModels: List<LibraryViewModel>) {
    if (this.libraryViewModels.size != libraryViewModels.size) {
      this.libraryViewModels.clear()
      this.libraryViewModels.addAll(libraryViewModels)
    }
    this.emptyViewVisibility = if (this.libraryViewModels.size > 0) View.GONE else View.VISIBLE
    this.recyclerViewVisibility = if (this.libraryViewModels.size > 0) View.VISIBLE else View.GONE
    notifyPropertyChanged(BR.emptyViewVisibility)
    notifyPropertyChanged(BR.recyclerViewVisibility)
  }
}
