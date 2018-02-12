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

package com.ayatk.biblio.ui.detail

import android.arch.lifecycle.ViewModel
import com.ayatk.biblio.di.ViewModelKey
import com.ayatk.biblio.ui.detail.info.NovelInfoFragment
import com.ayatk.biblio.ui.detail.info.NovelInfoViewModel
import com.ayatk.biblio.ui.detail.table.NovelTableFragment
import com.ayatk.biblio.ui.detail.table.NovelTableViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
interface NovelDetailModule {

  @ContributesAndroidInjector
  fun contributeNovelInfoFragment(): NovelInfoFragment

  @ContributesAndroidInjector
  fun contributeNovelTableFragment(): NovelTableFragment

  @Binds
  @IntoMap
  @ViewModelKey(NovelInfoViewModel::class)
  fun bindNovelInfoViewModel(novelInfoViewModel: NovelInfoViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(NovelTableViewModel::class)
  fun bindNovelTableViewModel(novelTableViewModel: NovelTableViewModel): ViewModel
}
