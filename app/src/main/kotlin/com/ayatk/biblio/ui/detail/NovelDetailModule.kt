/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.detail

import com.ayatk.biblio.ui.detail.info.NovelInfoFragment
import com.ayatk.biblio.ui.detail.table.NovelTableFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NovelDetailModule {

  @ContributesAndroidInjector
  abstract fun contributeNovelInfoFragment(): NovelInfoFragment

  @ContributesAndroidInjector
  abstract fun contributeNovelTableFragment(): NovelTableFragment
}
