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

import android.app.AlertDialog
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.content.systemService
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.DateFormat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import mabbas007.tagsedittext.TagsEditText
import timber.log.Timber
import javax.inject.Inject

class NovelInfoViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  lateinit var novel: Novel

  var tags = MutableLiveData<List<String>>()

  fun lastUpdate(): String = DateFormat.yyyyMMddkkmmJP.format(novel.lastUpdateDate)

  fun url(): String = novel.publisher.url + novel.code.toLowerCase()

  fun start() {
    libraryRepository.find(novel)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { library -> tags.postValue(library.tag) },
            Timber::e
        )
        .addTo(compositeDisposable)
  }

  fun onClickWriter(context: Context) {
    Navigator.navigateToWebPage(context, "http://mypage.syosetu.com/" + novel.writerId)
  }

  fun onClickNovelPage(context: Context) {
    Navigator.navigateToWebPage(context, "http://ncode.syosetu.com/" + novel.code.toLowerCase())
  }

  // TODO: 2017/11/26 context持ってるのでFragmentに移動させる
  fun onClickUserTag(context: Context) {
    val imm = context.systemService<InputMethodManager>()

    val editView = TagsEditText(context)

    editView.hint = "追加するタグを入力"
    editView.setTags()
    val dialog = AlertDialog.Builder(context)
        .setTitle("タグの追加")
        .setView(editView)
        .setPositiveButton("OK") { _, _ ->
          libraryRepository.save(Library(novel = novel, tag = editView.tags)).subscribe()
          tags.postValue(editView.tags)
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .setNegativeButton("キャンセル") { _, _ ->
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .create()

    libraryRepository.find(novel)
        .subscribe({ library -> editView.setTags(*library.tag.toTypedArray()) })
        .addTo(compositeDisposable)

    dialog.show()
    editView.requestFocus()
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
