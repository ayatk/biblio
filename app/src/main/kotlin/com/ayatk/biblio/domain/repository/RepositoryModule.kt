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

package com.ayatk.biblio.domain.repository

import com.ayatk.biblio.data.datasource.library.LibraryDataSource
import com.ayatk.biblio.data.datasource.library.LibraryLocalDataSource
import com.ayatk.biblio.data.datasource.novel.NovelBodyDataSource
import com.ayatk.biblio.data.datasource.novel.NovelBodyLocalDataSource
import com.ayatk.biblio.data.datasource.novel.NovelBodyRemoteDataSource
import com.ayatk.biblio.data.datasource.novel.NovelDataSource
import com.ayatk.biblio.data.datasource.novel.NovelLocalDataSource
import com.ayatk.biblio.data.datasource.novel.NovelRemoteDataSource
import com.ayatk.biblio.data.datasource.novel.NovelTableDataSource
import com.ayatk.biblio.data.datasource.novel.NovelTableLocalDataSource
import com.ayatk.biblio.data.datasource.novel.NovelTableRemoteDataSource
import com.ayatk.biblio.data.datasource.ranking.RankingRemoteDataSource
import com.ayatk.biblio.data.narou.NarouClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun provideLibraryRepository(localDataSource: LibraryLocalDataSource): LibraryRepository =
      LibraryDataSource(localDataSource)

  @Singleton
  @Provides
  fun provideNovelBodyRepository(
      local: NovelBodyLocalDataSource,
      remote: NovelBodyRemoteDataSource
  ): NovelBodyRepository = NovelBodyDataSource(local, remote)

  @Singleton
  @Provides
  fun provideNovelRepository(
      local: NovelLocalDataSource,
      remote: NovelRemoteDataSource
  ): NovelRepository = NovelDataSource(local, remote)

  @Singleton
  @Provides
  fun provideNovelTableRepository(
      local: NovelTableLocalDataSource,
      remote: NovelTableRemoteDataSource
  ): NovelTableRepository = NovelTableDataSource(local, remote)

  @Singleton
  @Provides
  fun provideRankingRemoteDataSource(client: NarouClient): RankingRepository =
      RankingRemoteDataSource(client)
}
