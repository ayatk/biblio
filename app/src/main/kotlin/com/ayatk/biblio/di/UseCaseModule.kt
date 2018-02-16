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

package com.ayatk.biblio.di

import com.ayatk.biblio.data.repository.LibraryRepository
import com.ayatk.biblio.data.repository.RankingRepository
import com.ayatk.biblio.domain.usecase.HomeLibraryUseCase
import com.ayatk.biblio.domain.usecase.HomeLibraryUseCaseImpl
import com.ayatk.biblio.domain.usecase.HomeRankingUseCase
import com.ayatk.biblio.domain.usecase.HomeRankingUseCaseImpl
import com.ayatk.biblio.domain.usecase.RankingUseCase
import com.ayatk.biblio.domain.usecase.RankingUseCaseImpl
import com.ayatk.biblio.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

  @Singleton
  @Provides
  fun provideHomeLibraryUseCase(
      repository: LibraryRepository,
      schedulerProvider: SchedulerProvider
  ): HomeLibraryUseCase = HomeLibraryUseCaseImpl(repository, schedulerProvider)

  @Singleton
  @Provides
  fun provideHomeRankingUseCase(
      repository: RankingRepository,
      schedulerProvider: SchedulerProvider
  ): HomeRankingUseCase = HomeRankingUseCaseImpl(repository, schedulerProvider)

  @Singleton
  @Provides
  fun provideRankingUseCase(
      repository: RankingRepository,
      schedulerProvider: SchedulerProvider
  ): RankingUseCase = RankingUseCaseImpl(repository, schedulerProvider)
}
