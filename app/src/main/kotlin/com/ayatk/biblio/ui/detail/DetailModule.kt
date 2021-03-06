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

import androidx.lifecycle.ViewModel
import com.ayatk.biblio.di.ViewModelKey
import com.ayatk.biblio.ui.detail.index.IndexFragment
import com.ayatk.biblio.ui.detail.index.IndexViewModel
import com.ayatk.biblio.ui.detail.info.InfoFragment
import com.ayatk.biblio.ui.detail.info.InfoViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
interface DetailModule {

  @ContributesAndroidInjector
  fun contributeInfoFragment(): InfoFragment

  @ContributesAndroidInjector
  fun contributeIndexFragment(): IndexFragment

  @Binds
  @IntoMap
  @ViewModelKey(InfoViewModel::class)
  fun bindInfoViewModel(infoViewModel: InfoViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(IndexViewModel::class)
  fun bindIndexViewModel(indexViewModel: IndexViewModel): ViewModel
}
