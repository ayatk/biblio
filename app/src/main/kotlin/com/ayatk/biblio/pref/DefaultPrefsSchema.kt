/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.pref

import android.support.annotation.StyleRes
import com.ayatk.biblio.R
import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

@Table(name = "com.ayatk.novella.preferences")
class DefaultPrefsSchema {

  @Key(name = "app_theme")
  @StyleRes
  val appTheme = R.style.AppTheme

  @Key(name = "text_background_color")
  val textBackgroundColor = "ffffff"

  @Key(name = "show_tag_at_library")
  val showTagAtLibrary = false
}
