/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view

import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.view.fragment.BookmarkFragment
import com.ayatk.biblio.view.fragment.LibraryFragment
import com.ayatk.biblio.view.fragment.RankingFragment
import com.ayatk.biblio.view.fragment.SettingFragment


enum class Page constructor(
    @MenuRes val menuId: Int,
    @StringRes val titleResId: Int,
    val toggleToolbar: Boolean,
    val pageName: String
) {
  LIBRARY(R.id.nav_library, R.string.library, true, LibraryFragment::class.java.simpleName) {
    override fun createFragment() = LibraryFragment.newInstance()
  },
  BOOKMARK(R.id.nav_bookmark, R.string.bookmark, true, BookmarkFragment::class.java.simpleName) {
    override fun createFragment() = BookmarkFragment.newInstance()
  },
  RANKING(R.id.nav_ranking, R.string.ranking, true, RankingFragment::class.java.simpleName) {
    override fun createFragment() = RankingFragment.newInstance()
  },
  //  SEARCH(R.id.nav_search, R.string.search, true, SearchFragment::class.java.simpleName) {
//    override fun createFragment() = SearchFragment.newInstance()
//  },
  SETTINGS(R.id.nav_settings, R.string.setting, true, SettingFragment::class.java.simpleName) {
    override fun createFragment() = SettingFragment.newInstance()
  };

  abstract fun createFragment(): Fragment

  companion object {

    fun forMenuId(item: MenuItem): Page {
      val id = item.itemId
      return forMenuId(id)
    }

    fun forMenuId(@MenuRes id: Int): Page {
      values()
          .filter { it.menuId == id }
          .forEach { return it }
      throw AssertionError("no menu enum found for the id. you forgot to implement?")
    }

    fun forName(fragment: Fragment): Page {
      val name = fragment.javaClass.simpleName
      values()
          .filter { it.pageName == name }
          .forEach { return it }
      throw AssertionError("no menu enum found for the id. you forgot to implement?")
    }
  }

}
