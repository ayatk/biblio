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

import com.ayatk.biblio.data.datasource.novel.EpisodeRemoteDataSource
import com.ayatk.biblio.data.datasource.novel.IndexRemoteDataSource
import com.ayatk.biblio.data.datasource.novel.NovelRemoteDataSource
import com.ayatk.biblio.data.db.EpisodeDatabase
import com.ayatk.biblio.data.db.IndexDatabase
import com.ayatk.biblio.data.db.LibraryDatabase
import com.ayatk.biblio.data.db.NovelDatabase
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.repository.EpisodeRepository
import com.ayatk.biblio.data.repository.EpisodeRepositoryImpl
import com.ayatk.biblio.data.repository.IndexRepository
import com.ayatk.biblio.data.repository.IndexRepositoryImpl
import com.ayatk.biblio.data.repository.LibraryRepository
import com.ayatk.biblio.data.repository.LibraryRepositoryImpl
import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.data.repository.NovelRepositoryImpl
import com.ayatk.biblio.data.repository.RankingRepository
import com.ayatk.biblio.data.repository.RankingRepositoryImpl
import com.ayatk.biblio.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun provideLibraryRepository(
      database: LibraryDatabase
  ): LibraryRepository = LibraryRepositoryImpl(database)

  @Singleton
  @Provides
  fun provideEpisodeRepository(
      local: EpisodeDatabase,
      remote: EpisodeRemoteDataSource,
      schedulerProvider: SchedulerProvider
  ): EpisodeRepository = EpisodeRepositoryImpl(local, remote, schedulerProvider)

  @Singleton
  @Provides
  fun provideNovelRepository(
      local: NovelDatabase,
      remote: NovelRemoteDataSource,
      schedulerProvider: SchedulerProvider
  ): NovelRepository = NovelRepositoryImpl(local, remote, schedulerProvider)

  @Singleton
  @Provides
  fun provideIndexRepository(
      local: IndexDatabase,
      remote: IndexRemoteDataSource
  ): IndexRepository = IndexRepositoryImpl(local, remote)

  @Singleton
  @Provides
  fun provideRankingRemoteDataSource(
      client: NarouClient
  ): RankingRepository = RankingRepositoryImpl(client)
}
