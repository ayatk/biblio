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
import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.body.NovelBodyModule
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import com.ayatk.biblio.ui.detail.NovelDetailModule
import com.ayatk.biblio.ui.home.HomeActivity
import com.ayatk.biblio.ui.home.HomeModule
import com.ayatk.biblio.ui.ranking.RankingActivity
import com.ayatk.biblio.ui.ranking.RankingModule
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

  @ContributesAndroidInjector(modules = [RankingModule::class])
  abstract fun contributeRankingActivity(): RankingActivity
}
