/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import com.ayatk.biblio.di.scope.FragmentScope
import com.ayatk.biblio.view.fragment.BookmarkFragment
import com.ayatk.biblio.view.fragment.LibraryFragment
import com.ayatk.biblio.view.fragment.NovelBodyFragment
import com.ayatk.biblio.view.fragment.NovelInfoFragment
import com.ayatk.biblio.view.fragment.NovelTableFragment
import com.ayatk.biblio.view.fragment.RankingFragment
import com.ayatk.biblio.view.fragment.SearchFragment
import com.ayatk.biblio.view.fragment.SettingFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

  fun inject(fragment: LibraryFragment)

  fun inject(fragment: BookmarkFragment)

  fun inject(fragment: RankingFragment)

  fun inject(fragment: SearchFragment)

  fun inject(fragment: SettingFragment)

  fun inject(fragment: NovelTableFragment)

  fun inject(fragment: NovelInfoFragment)

  fun inject(fragment: NovelBodyFragment)
}
