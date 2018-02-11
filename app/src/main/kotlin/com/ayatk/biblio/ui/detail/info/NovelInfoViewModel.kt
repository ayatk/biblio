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
import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.inputmethod.InputMethodManager
import androidx.content.systemService
import com.ayatk.biblio.BR
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.DateFormat
import mabbas007.tagsedittext.TagsEditText

class NovelInfoViewModel(
    private val libraryRepository: LibraryRepository,
    val novel: Novel
) : BaseObservable(), ViewModel {

  //  fun novelGenre(): String {
  //    when (novel.genre) {
  //      Genre.LOVE_DIFF_WORLD   -> return context.getString(R.string.genre_love_diff_world)
  //      Genre.LOVE_REAL_WORLD   -> return context.getString(R.string.genre_love_real_world)
  //      Genre.FANTASY_HIGH      -> return context.getString(R.string.genre_fantasy_high)
  //      Genre.FANTASY_LOW       -> return context.getString(R.string.genre_fantasy_low)
  //      Genre.LITERAL_COMEDY    -> return context.getString(R.string.genre_literal_comedy)
  //      Genre.LITERAL_ACTION    -> return context.getString(R.string.genre_literal_action)
  //      Genre.LITERAL_DETECTIVE -> return context.getString(R.string.genre_literal_detective)
  //      Genre.LITERAL_PURE      -> return context.getString(R.string.genre_literal_pure)
  //      Genre.LITERAL_DRAMA     -> return context.getString(R.string.genre_literal_drama)
  //      Genre.LITERAL_HISTORY   -> return context.getString(R.string.genre_literal_history)
  //      Genre.LITERAL_HORROR    -> return context.getString(R.string.genre_literal_horror)
  //      Genre.SF_VR             -> return context.getString(R.string.genre_sf_vr)
  //      Genre.SF_PANIC          -> return context.getString(R.string.genre_sf_panic)
  //      Genre.SF_SCIENCE        -> return context.getString(R.string.genre_sf_science)
  //      Genre.SF_SPACE          -> return context.getString(R.string.genre_sf_space)
  //      Genre.OTHER_ESSAY       -> return context.getString(R.string.genre_other_essay)
  //      Genre.OTHER_FAIRYTALE   -> return context.getString(R.string.genre_other_fairytale)
  //      Genre.OTHER_REPLAY      -> return context.getString(R.string.genre_other_replay)
  //      Genre.OTHER_POEM        -> return context.getString(R.string.genre_other_poem)
  //      Genre.OTHER             -> return context.getString(R.string.genre_other)
  //      Genre.NONGENRE          -> return context.getString(R.string.genre_nongenre)
  //    }
  //  }

  @Bindable
  var tags = listOf<String>()

  fun lastUpdate(): String = DateFormat.yyyyMMddkkmmJP.format(novel.lastUpdateDate)

  fun url(): String = novel.publisher.url + novel.code.toLowerCase()

  fun start() {
    libraryRepository.find(novel)
        .subscribe(
            { library ->
              tags = library.tag
              notifyPropertyChanged(BR.tags)
            }
        )
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
          tags = editView.tags
          notifyPropertyChanged(BR.tags)
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .setNegativeButton("キャンセル") { _, _ ->
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .create()

    libraryRepository.find(novel)
        .subscribe({ library -> editView.setTags(*library.tag.toTypedArray()) })

    dialog.show()
    editView.requestFocus()
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
  }

  override fun destroy() {}
}
