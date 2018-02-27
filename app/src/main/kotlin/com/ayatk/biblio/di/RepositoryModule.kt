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
import com.ayatk.biblio.data.db.dao.EpisodeDao
import com.ayatk.biblio.data.db.dao.IndexDao
import com.ayatk.biblio.data.db.dao.NovelDao
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.repository.EpisodeRepository
import com.ayatk.biblio.data.repository.EpisodeRepositoryImpl
import com.ayatk.biblio.data.repository.IndexRepository
import com.ayatk.biblio.data.repository.IndexRepositoryImpl
import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.data.repository.NovelRepositoryImpl
import com.ayatk.biblio.data.repository.RankingRepository
import com.ayatk.biblio.data.repository.RankingRepositoryImpl
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
      dao: EpisodeDao,
      remote: EpisodeRemoteDataSource
  ): EpisodeRepository = EpisodeRepositoryImpl(dao, remote)

  @Singleton
  @Provides
  fun provideNovelRepository(
      dao: NovelDao
  ): NovelRepository = NovelRepositoryImpl(dao)

  @Singleton
  @Provides
  fun provideIndexRepository(
      dao: IndexDao,
      remote: IndexRemoteDataSource
  ): IndexRepository = IndexRepositoryImpl(dao, remote)

  @Singleton
  @Provides
  fun provideRankingRemoteDataSource(
      client: NarouClient
  ): RankingRepository = RankingRepositoryImpl(client)
}
