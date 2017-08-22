/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio

import com.facebook.stetho.Stetho
import com.tomoima.debot.DebotConfigurator
import jp.wasabeef.takt.Takt

class DebugApp : App() {
  override fun onCreate() {
    super.onCreate()

    Stetho.initializeWithDefaults(this)
    DebotConfigurator.configureWithDefault(this);
    Takt.stock(this).play()
  }

  override fun onTerminate() {
    Takt.finish()
    super.onTerminate()
  }
}
