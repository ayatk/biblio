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

import com.ayatk.biblio.data.datasource.library.LibraryDataSource
import com.ayatk.biblio.data.datasource.library.LibraryLocalDataSource
import com.ayatk.biblio.data.datasource.novel.EpisodeDataSource
import com.ayatk.biblio.data.datasource.novel.EpisodeLocalDataSource
import com.ayatk.biblio.data.datasource.novel.EpisodeRemoteDataSource
import com.ayatk.biblio.data.datasource.novel.IndexDataSource
import com.ayatk.biblio.data.datasource.novel.IndexLocalDataSource
import com.ayatk.biblio.data.datasource.novel.IndexRemoteDataSource
import com.ayatk.biblio.data.datasource.novel.NovelDataSource
import com.ayatk.biblio.data.datasource.novel.NovelLocalDataSource
import com.ayatk.biblio.data.datasource.novel.NovelRemoteDataSource
import com.ayatk.biblio.data.datasource.ranking.RankingRemoteDataSource
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.domain.repository.EpisodeRepository
import com.ayatk.biblio.domain.repository.IndexRepository
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.domain.repository.NovelRepository
import com.ayatk.biblio.domain.repository.RankingRepository
import com.ayatk.biblio.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun provideLibraryRepository(
      localDataSource: LibraryLocalDataSource,
      schedulerProvider: SchedulerProvider
  ): LibraryRepository =
      LibraryDataSource(localDataSource, schedulerProvider)

  @Singleton
  @Provides
  fun provideEpisodeRepository(
      local: EpisodeLocalDataSource,
      remote: EpisodeRemoteDataSource,
      schedulerProvider: SchedulerProvider
  ): EpisodeRepository = EpisodeDataSource(local, remote, schedulerProvider)

  @Singleton
  @Provides
  fun provideNovelRepository(
      local: NovelLocalDataSource,
      remote: NovelRemoteDataSource,
      schedulerProvider: SchedulerProvider
  ): NovelRepository = NovelDataSource(local, remote, schedulerProvider)

  @Singleton
  @Provides
  fun provideIndexRepository(
      local: IndexLocalDataSource,
      remote: IndexRemoteDataSource,
      schedulerProvider: SchedulerProvider
  ): IndexRepository = IndexDataSource(local, remote, schedulerProvider)

  @Singleton
  @Provides
  fun provideRankingRemoteDataSource(
      client: NarouClient,
      schedulerProvider: SchedulerProvider
  ): RankingRepository =
      RankingRemoteDataSource(client, schedulerProvider)
}
