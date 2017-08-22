/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio

import android.app.Application
import com.ayatk.biblio.di.AppComponent
import com.ayatk.biblio.di.AppModule
import com.ayatk.biblio.di.DaggerAppComponent
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric


open class App : Application() {

  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()

    Fabric.with(this, Crashlytics())

    appComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()

    initLeakCanary()
  }

  fun component(): AppComponent = appComponent

  private fun initLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      return
    }
    LeakCanary.install(this)
  }
}
