/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.home

import com.ayatk.biblio.ui.home.bookmark.BookmarkFragment
import com.ayatk.biblio.ui.home.library.LibraryFragment
import com.ayatk.biblio.ui.home.ranking.RankingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

  @ContributesAndroidInjector
  abstract fun contributeLibraryFragment(): LibraryFragment

  @ContributesAndroidInjector
  abstract fun contributeBookmarkFragment(): BookmarkFragment

  @ContributesAndroidInjector
  abstract fun contributeRankingFragment(): RankingFragment

}
