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

package com.ayatk.biblio.util

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.view.ViewPager
import androidx.os.bundleOf
import com.ayatk.biblio.App
import com.ayatk.biblio.R
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

object Analytics {

  private lateinit var tracker: FirebaseAnalytics
  private lateinit var app: App

  enum class Screen {
    HOME,
    LIBRARY,
    BOOKMARK,
    TOP_RANKING,
    SETTINGS,
    DETAIL,
    INDEX,
    INFO,
    EPISODE,
    LICENSE,
    RANKING,
    RANKING_LIST,
    SEARCH
  }

  enum class Category {
    EPISODE,
    DETAIL,
    SEARCH,
    HOME,
    LIBRARY,
    BOOKMARK,
    RANKING,
    SETTINGS,
  }

  interface Action {
    val category: Category
  }

  enum class HomeAction : Action {
    SELECT_LIBRARY_NAV,
    SELECT_BOOKMARK_NAV,
    SELECT_TOP_RANKING_NAV,
    SELECT_SETTINGS_NAV;

    override val category: Category = Category.HOME
  }

  enum class LibraryAction : Action {
    REFRESH_LIBRARIES,
    TAP_CELL_MENU,
    DIALOG_NOVEL_DELETE_VIEWED,
    DIALOG_NOVEL_DELETE_CANCEL,
    DELETE_NOVEL;

    override val category: Category = Category.LIBRARY
  }

  enum class DetailAction : Action {
    VIEW_VIA_HOME_LIBRARY,
    VIEW_VIA_SEARCH_RESULT,
    VIEW_VIA_RANKING_LIST,
    VIEW_VIA_TOP_RANKING,
    VIEW_TABLE_OF_CONTENTS,
    VIEW_NOVEL_INFO;

    override val category: Category = Category.DETAIL
  }

  fun init(app: App) {
    tracker = FirebaseAnalytics.getInstance(app)
    this.app = app
  }

  fun event(action: Action, value: String?) {
    val bundle = bundleOf(
        FirebaseAnalytics.Param.ITEM_CATEGORY to action.category.name,
        FirebaseAnalytics.Param.ITEM_NAME to action,
        FirebaseAnalytics.Param.VALUE to value
    )

    Timber.v(app.getString(R.string.log_analytics_debug, action.category.name, action, value))

    tracker.logEvent(action.toString(), bundle)
    Crashlytics.log(String.format("%1\$s_%2\$s_%3\$s", action.category, action, value))
  }

  fun viewPagerEvent(viewPager: ViewPager, event: (position: Int) -> Unit) {
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {}

      override fun onPageScrolled(
          position: Int, positionOffset: Float, positionOffsetPixels: Int
      ) {
      }

      override fun onPageSelected(position: Int) {
       event(position)
      }
    })
  }

  class ScreenLog(private val screen: Screen) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() = lifecycleEvent(Lifecycle.Event.ON_CREATE)

    private fun lifecycleEvent(event: Lifecycle.Event) {
      val bundle = bundleOf(
          FirebaseAnalytics.Param.ITEM_CATEGORY to "SCREEN_LOG",
          FirebaseAnalytics.Param.ITEM_NAME to screen.name,
          FirebaseAnalytics.Param.VALUE to event.name
      )

      Timber.v(app.getString(R.string.log_analytics_debug, "SCREEN_LOG", screen.name, event.name))

      tracker.logEvent("ga_" + screen.name, bundle)
      Crashlytics.log(String.format("ga_%1\$s_%2\$s_%3\$s", "SCREEN_LOG", screen.name, event.name))
    }
  }
}
