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

package com.ayatk.biblio.ui.detail.info

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ayatk.biblio.domain.usecase.DetailUseCase
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.util.helper.navigateToWebPage
import com.ayatk.biblio.util.DatePattern
import com.ayatk.biblio.util.format
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class InfoViewModel @Inject constructor(
  private val useCase: DetailUseCase,
  private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

  private val compositeDisposable = CompositeDisposable()

  lateinit var novel: Novel

  var tags = MutableLiveData<List<String>>()

  fun lastUpdate(): String = novel.lastUpload.format(DatePattern.YYYY_MM_DD_KK_MM_JP)

  fun url(): String = novel.publisher.url + novel.code.toLowerCase()

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun start() {
    useCase.getLibrary(novel)
      .subscribeOn(schedulerProvider.io())
      .observeOn(schedulerProvider.ui())
      .subscribe(
        { library -> tags.postValue(library.tag) },
        Timber::e
      )
      .addTo(compositeDisposable)
  }

  fun onClickWriter(context: Context) {
    context.navigateToWebPage("http://mypage.syosetu.com/" + novel.userID)
  }

  fun onClickNovelPage(context: Context) {
    context.navigateToWebPage("http://ncode.syosetu.com/" + novel.code.toLowerCase())
  }

  //   TODO: 2017/11/26 context持ってるのでFragmentに移動させる
  //  fun onClickUserTag(context: Context) {
  //    val imm = context.systemService<InputMethodManager>()
  //
  //    val editView = TagsEditText(context)
  //
  //    editView.hint = "追加するタグを入力"
  //    editView.setTags()
  //    val dialog = AlertDialog.Builder(context)
  //        .setTitle("タグの追加")
  //        .setView(editView)
  //        .setPositiveButton("OK") { _, _ ->
  //          libraryRepository.update(listOf(Library(novel = novel, tag = editView.tags)))
  //              .subscribeOn(schedulerProvider.io())
  //              .observeOn(schedulerProvider.ui())
  //              .subscribe()
  //          tags.postValue(editView.tags)
  //          imm.hideSoftInputFromWindow(editView.windowToken, 0)
  //        }
  //        .setNegativeButton("キャンセル") { _, _ ->
  //          imm.hideSoftInputFromWindow(editView.windowToken, 0)
  //        }
  //        .create()
  //
  //    libraryRepository.find(novel)
  //        .subscribeOn(schedulerProvider.io())
  //        .observeOn(schedulerProvider.ui())
  //        .subscribe({ library -> editView.setTags(*library.tag.toTypedArray()) })
  //        .addTo(compositeDisposable)
  //
  //    dialog.show()
  //    editView.requestFocus()
  //    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
  //  }
}
