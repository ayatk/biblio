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

import android.support.annotation.IdRes
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import com.ayatk.biblio.R
import com.ayatk.biblio.ui.home.bookmark.BookmarkFragment
import com.ayatk.biblio.ui.home.library.LibraryFragment
import com.ayatk.biblio.ui.home.ranking.TopRankingFragment
import com.ayatk.biblio.ui.home.setting.SettingFragment
import com.ayatk.biblio.util.Analytics

enum class Page(
    @MenuRes val menuId: Int,
    @StringRes val titleResId: Int,
    val toggleToolbar: Boolean,
    val action: Analytics.HomeAction
) {
  LIBRARY(R.id.nav_library, R.string.library, true, Analytics.HomeAction.SELECT_LIBRARY_NAV) {
    override fun createFragment() = LibraryFragment.newInstance()
  },
  BOOKMARK(R.id.nav_bookmark, R.string.bookmark, true, Analytics.HomeAction.SELECT_BOOKMARK_NAV) {
    override fun createFragment() = BookmarkFragment.newInstance()
  },
  RANKING(R.id.nav_ranking, R.string.ranking, true, Analytics.HomeAction.SELECT_TOP_RANKING_NAV) {
    override fun createFragment() = TopRankingFragment.newInstance()
  },
  SETTINGS(R.id.nav_settings, R.string.setting, true, Analytics.HomeAction.SELECT_SETTINGS_NAV) {
    override fun createFragment() = SettingFragment.newInstance()
  };

  abstract fun createFragment(): Fragment

  companion object {
    fun forMenuId(@IdRes id: Int): Page = values().firstOrNull { it.menuId == id } ?: LIBRARY
  }
}
