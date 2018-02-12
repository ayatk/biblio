/*
 * Copyright (c) 2016-2018 ayatk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ayatk.biblio.ui.home

import android.arch.lifecycle.ViewModel
import com.ayatk.biblio.di.ViewModelKey
import com.ayatk.biblio.ui.home.bookmark.BookmarkFragment
import com.ayatk.biblio.ui.home.library.LibraryFragment
import com.ayatk.biblio.ui.home.library.LibraryViewModel
import com.ayatk.biblio.ui.home.ranking.TopRankingFragment
import com.ayatk.biblio.ui.home.ranking.TopRankingViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
interface HomeModule {

  @ContributesAndroidInjector
  fun contributeLibraryFragment(): LibraryFragment

  @ContributesAndroidInjector
  fun contributeBookmarkFragment(): BookmarkFragment

  @ContributesAndroidInjector
  fun contributeRankingFragment(): TopRankingFragment

  @Binds
  @IntoMap
  @ViewModelKey(LibraryViewModel::class)
  fun bindLibraryViewModel(libraryViewModel: LibraryViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(TopRankingViewModel::class)
  fun bindTopRankingViewModel(topRankingViewModel: TopRankingViewModel): ViewModel
}
