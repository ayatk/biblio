/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.body

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NovelBodyModule {

  @ContributesAndroidInjector
  abstract fun contributeNovelBodyFragment(): NovelBodyFragment
}
