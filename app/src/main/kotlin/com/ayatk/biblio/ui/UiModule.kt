/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui

import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.body.NovelBodyModule
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import com.ayatk.biblio.ui.detail.NovelDetailModule
import com.ayatk.biblio.ui.home.HomeActivity
import com.ayatk.biblio.ui.home.HomeModule
import com.ayatk.biblio.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class UiModule {

  @ContributesAndroidInjector(modules = arrayOf(HomeModule::class))
  internal abstract fun contributeHomeActivity(): HomeActivity

  @ContributesAndroidInjector(modules = arrayOf(NovelBodyModule::class))
  internal abstract fun contributeBodyActivity(): NovelBodyActivity

  @ContributesAndroidInjector(modules = arrayOf(NovelDetailModule::class))
  internal abstract fun contributeDetailActivity(): NovelDetailActivity

  @ContributesAndroidInjector
  internal abstract fun contributeSearchActivity(): SearchActivity
}
