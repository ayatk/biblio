/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui

import android.app.Activity
import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.body.NovelBodyModule
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import com.ayatk.biblio.ui.detail.NovelDetailModule
import com.ayatk.biblio.ui.home.HomeActivity
import com.ayatk.biblio.ui.home.HomeModule
import com.ayatk.biblio.ui.search.SearchActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class UiModule {

  @Binds
  abstract fun bindActivityContext(activity: Activity): Activity

  @ContributesAndroidInjector(modules = [(HomeModule::class)])
  internal abstract fun contributeHomeActivity(): HomeActivity

  @ContributesAndroidInjector(modules = [(NovelBodyModule::class)])
  internal abstract fun contributeBodyActivity(): NovelBodyActivity

  @ContributesAndroidInjector(modules = [(NovelDetailModule::class)])
  internal abstract fun contributeDetailActivity(): NovelDetailActivity

  @ContributesAndroidInjector
  internal abstract fun contributeSearchActivity(): SearchActivity
}
