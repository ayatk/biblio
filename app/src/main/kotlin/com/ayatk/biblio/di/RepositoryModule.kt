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

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.repository.library.LibraryDataSource
import com.ayatk.biblio.repository.library.LibraryLocalDataSource
import com.ayatk.biblio.repository.library.LibraryDataRepository
import com.ayatk.biblio.repository.novel.NovelBodyDataSource
import com.ayatk.biblio.repository.novel.NovelBodyLocalDataSource
import com.ayatk.biblio.repository.novel.NovelBodyRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelBodyDataRepository
import com.ayatk.biblio.repository.novel.NovelDataSource
import com.ayatk.biblio.repository.novel.NovelLocalDataSource
import com.ayatk.biblio.repository.novel.NovelRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelDataRepository
import com.ayatk.biblio.repository.novel.NovelTableDataSource
import com.ayatk.biblio.repository.novel.NovelTableLocalDataSource
import com.ayatk.biblio.repository.novel.NovelTableRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelTableDataRepository
import com.ayatk.biblio.repository.ranking.RankingDataSource
import com.ayatk.biblio.repository.ranking.RankingRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun provideLibraryRepository(localDataSource: LibraryLocalDataSource): LibraryDataSource =
      LibraryDataRepository(localDataSource)

  @Singleton
  @Provides
  fun provideNovelBodyRepository(
      local: NovelBodyLocalDataSource,
      remote: NovelBodyRemoteDataSource
  ): NovelBodyDataSource = NovelBodyDataRepository(local, remote)

  @Singleton
  @Provides
  fun provideNovelRepository(
      local: NovelLocalDataSource,
      remote: NovelRemoteDataSource
  ): NovelDataSource = NovelDataRepository(local, remote)

  @Singleton
  @Provides
  fun provideNovelTableRepository(
      local: NovelTableLocalDataSource,
      remote: NovelTableRemoteDataSource
  ): NovelTableDataSource = NovelTableDataRepository(local, remote)

  @Singleton
  @Provides
  fun provideRankingRemoteDataSource(client: NarouClient): RankingDataSource =
      RankingRemoteDataSource(client)
}
