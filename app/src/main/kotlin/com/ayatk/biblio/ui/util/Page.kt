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

package com.ayatk.biblio.ui.util

import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.ui.home.bookmark.BookmarkFragment
import com.ayatk.biblio.ui.home.library.LibraryFragment
import com.ayatk.biblio.ui.home.ranking.RankingFragment
import com.ayatk.biblio.ui.home.setting.SettingFragment

enum class Page(
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
