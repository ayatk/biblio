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

package com.ayatk.biblio.ui

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.ayatk.biblio.di.ViewModelKey
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import com.ayatk.biblio.ui.detail.NovelDetailModule
import com.ayatk.biblio.ui.episode.EpisodeActivity
import com.ayatk.biblio.ui.episode.EpisodeModule
import com.ayatk.biblio.ui.home.HomeActivity
import com.ayatk.biblio.ui.home.HomeModule
import com.ayatk.biblio.ui.ranking.RankingActivity
import com.ayatk.biblio.ui.ranking.RankingModule
import com.ayatk.biblio.ui.search.SearchActivity
import com.ayatk.biblio.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
interface UiModule {

  @Binds
  fun bindActivityContext(activity: Activity): Activity

  @ContributesAndroidInjector(modules = [(HomeModule::class)])
  fun contributeHomeActivity(): HomeActivity

  @ContributesAndroidInjector(modules = [(EpisodeModule::class)])
  fun contributeEpisodeActivity(): EpisodeActivity

  @ContributesAndroidInjector(modules = [(NovelDetailModule::class)])
  fun contributeDetailActivity(): NovelDetailActivity

  @ContributesAndroidInjector
  fun contributeSearchActivity(): SearchActivity

  @ContributesAndroidInjector(modules = [RankingModule::class])
  fun contributeRankingActivity(): RankingActivity

  @Binds
  @IntoMap
  @ViewModelKey(SearchViewModel::class)
  fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}
