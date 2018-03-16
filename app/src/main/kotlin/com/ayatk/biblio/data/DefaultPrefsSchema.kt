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

package com.ayatk.biblio.data

import android.support.annotation.StyleRes
import com.ayatk.biblio.BuildConfig
import com.ayatk.biblio.R
import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

@Suppress("unused")
@Table(name = "${BuildConfig.APPLICATION_ID}_preferences")
class DefaultPrefsSchema {

  @Key(name = "app_theme")
  @StyleRes
  val appTheme = R.style.AppTheme

  @Key(name = "text_background_color")
  val textBackgroundColor = "ffffff"

  @Key(name = "show_tag_at_library")
  val showTagAtLibrary = false

  @Key(name = "home_page_state")
  val homePageState = R.id.nav_library

  @Key(name = "allow_analytics_tracker")
  val allowAnalyticsTracker = true
}
