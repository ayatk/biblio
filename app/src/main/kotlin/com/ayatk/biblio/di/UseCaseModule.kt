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

import com.ayatk.biblio.domain.usecase.DetailUseCase
import com.ayatk.biblio.domain.usecase.DetailUseCaseImpl
import com.ayatk.biblio.domain.usecase.EpisodeUseCase
import com.ayatk.biblio.domain.usecase.EpisodeUseCaseImpl
import com.ayatk.biblio.domain.usecase.LibraryUseCase
import com.ayatk.biblio.domain.usecase.LibraryUseCaseImpl
import com.ayatk.biblio.domain.usecase.RankingUseCase
import com.ayatk.biblio.domain.usecase.RankingUseCaseImpl
import com.ayatk.biblio.domain.usecase.SearchUseCase
import com.ayatk.biblio.domain.usecase.SearchUseCaseImpl
import com.ayatk.biblio.domain.usecase.TopRankingUseCase
import com.ayatk.biblio.domain.usecase.TopRankingUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
@Suppress("unused")
interface UseCaseModule {

  @Binds
  fun bindDetailUseCase(useCase: DetailUseCaseImpl): DetailUseCase

  @Binds
  fun bindEpisodeUseCase(useCase: EpisodeUseCaseImpl): EpisodeUseCase

  @Binds
  fun bindSearchUseCase(useCase: SearchUseCaseImpl): SearchUseCase

  @Binds
  fun bindHomeLibraryUseCase(useCase: LibraryUseCaseImpl): LibraryUseCase

  @Binds
  fun bindHomeRankingUseCase(useCase: TopRankingUseCaseImpl): TopRankingUseCase

  @Binds
  fun bindRankingUseCase(useCase: RankingUseCaseImpl): RankingUseCase
}
