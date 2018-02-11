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
import com.ayatk.biblio.repository.library.LibraryDataRepository
import com.ayatk.biblio.repository.library.LibraryLocalDataSource
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.repository.novel.NovelBodyDataRepository
import com.ayatk.biblio.repository.novel.NovelBodyLocalDataSource
import com.ayatk.biblio.repository.novel.NovelBodyRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelBodyRepository
import com.ayatk.biblio.repository.novel.NovelDataRepository
import com.ayatk.biblio.repository.novel.NovelLocalDataSource
import com.ayatk.biblio.repository.novel.NovelRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelRepository
import com.ayatk.biblio.repository.novel.NovelTableDataRepository
import com.ayatk.biblio.repository.novel.NovelTableLocalDataSource
import com.ayatk.biblio.repository.novel.NovelTableRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelTableRepository
import com.ayatk.biblio.repository.ranking.RankingRemoteDataSource
import com.ayatk.biblio.repository.ranking.RankingRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun provideLibraryRepository(localDataSource: LibraryLocalDataSource): LibraryRepository =
      LibraryDataRepository(localDataSource)

  @Singleton
  @Provides
  fun provideNovelBodyRepository(
      local: NovelBodyLocalDataSource,
      remote: NovelBodyRemoteDataSource
  ): NovelBodyRepository = NovelBodyDataRepository(local, remote)

  @Singleton
  @Provides
  fun provideNovelRepository(
      local: NovelLocalDataSource,
      remote: NovelRemoteDataSource
  ): NovelRepository = NovelDataRepository(local, remote)

  @Singleton
  @Provides
  fun provideNovelTableRepository(
      local: NovelTableLocalDataSource,
      remote: NovelTableRemoteDataSource
  ): NovelTableRepository = NovelTableDataRepository(local, remote)

  @Singleton
  @Provides
  fun provideRankingRemoteDataSource(client: NarouClient): RankingRepository =
      RankingRemoteDataSource(client)
}
