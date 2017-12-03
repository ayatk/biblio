/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio

import com.ayatk.biblio.di.DaggerAppComponent
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric

open class App : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()

    Fabric.with(this, Crashlytics())
    initLeakCanary()
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent
        .builder()
        .application(this)
        .build()
  }

  private fun initLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      return
    }
    LeakCanary.install(this)
  }
}
