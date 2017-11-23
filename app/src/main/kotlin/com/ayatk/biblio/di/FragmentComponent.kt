/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import com.ayatk.biblio.di.scope.FragmentScope
import com.ayatk.biblio.ui.body.NovelBodyFragment
import com.ayatk.biblio.ui.detail.info.NovelInfoFragment
import com.ayatk.biblio.ui.detail.table.NovelTableFragment
import com.ayatk.biblio.ui.home.bookmark.BookmarkFragment
import com.ayatk.biblio.ui.home.library.LibraryFragment
import com.ayatk.biblio.ui.home.ranking.RankingFragment
import com.ayatk.biblio.ui.home.setting.SettingFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

  fun inject(fragment: LibraryFragment)

  fun inject(fragment: BookmarkFragment)

  fun inject(fragment: RankingFragment)

  fun inject(fragment: SettingFragment)

  fun inject(fragment: NovelTableFragment)

  fun inject(fragment: NovelInfoFragment)

  fun inject(fragment: NovelBodyFragment)
}
