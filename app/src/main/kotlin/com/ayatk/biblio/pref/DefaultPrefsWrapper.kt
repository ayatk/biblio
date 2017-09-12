/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.pref

import android.content.Context

/**
 * Dagger2でクリーンビルドするとkaptの順番で死ぬのでラップする
 */
class DefaultPrefsWrapper(context: Context) {
  val prefs: DefaultPrefs = DefaultPrefs.get(context)
}
