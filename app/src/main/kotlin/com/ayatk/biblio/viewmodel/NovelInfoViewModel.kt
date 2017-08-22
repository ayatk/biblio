/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.databinding.BaseObservable
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.view.helper.Navigator
import mabbas007.tagsedittext.TagsEditText
import java.text.SimpleDateFormat
import java.util.Locale

class NovelInfoViewModel
constructor(private val context: Context,
            private val navigator: Navigator,
            private val libraryRepository: LibraryRepository,
            val novel: Novel) : BaseObservable(), ViewModel {

  private val DATA_FORMAT = SimpleDateFormat("yyyy年MM月dd日 kk時mm分", Locale.getDefault())

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

  fun lastUpdate(): String = DATA_FORMAT.format(novel.lastUpdateDate)

  fun url(): String = novel.publisher.url + novel.code.toLowerCase()

  fun onClickWriter(@Suppress("UNUSED_PARAMETER") view: View) {
    navigator.navigateToWebPage("http://mypage.syosetu.com/" + novel.writerId)
  }

  fun onClickNovelPage(@Suppress("UNUSED_PARAMETER") view: View) {
    navigator.navigateToWebPage("http://ncode.syosetu.com/" + novel.code.toLowerCase())
  }

  fun onClickUserTag(@Suppress("UNUSED_PARAMETER") view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val editView = TagsEditText(context)

    editView.hint = "追加するタグを入力"
    editView.setTags()
    val dialog = AlertDialog.Builder(context)
        .setTitle("タグの追加")
        .setView(editView)
        .setPositiveButton("OK") { _, _ ->
          libraryRepository.save(Library(novel = novel, tag = editView.tags))
              .subscribe()
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .setNegativeButton("キャンセル") { _, _ ->
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .create()


    libraryRepository.find(novel)
        .subscribe(
            { library ->
              editView.setTags(*library.tag.toTypedArray())
            }
        )

    dialog.show()
    editView.requestFocus()
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
  }

  override fun destroy() {}
}
