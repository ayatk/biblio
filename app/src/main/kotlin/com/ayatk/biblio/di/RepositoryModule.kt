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

import com.ayatk.biblio.data.repository.BookmarkRepository
import com.ayatk.biblio.data.repository.BookmarkRepositoryImpl
import com.ayatk.biblio.data.repository.EpisodeRepository
import com.ayatk.biblio.data.repository.EpisodeRepositoryImpl
import com.ayatk.biblio.data.repository.IndexRepository
import com.ayatk.biblio.data.repository.IndexRepositoryImpl
import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.data.repository.NovelRepositoryImpl
import com.ayatk.biblio.data.repository.RankingRepository
import com.ayatk.biblio.data.repository.RankingRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
@Suppress("unused")
interface RepositoryModule {

  @Binds
  fun bindBookmarkRepository(repository: BookmarkRepositoryImpl): BookmarkRepository

  @Binds
  fun bindEpisodeRepository(repository: EpisodeRepositoryImpl): EpisodeRepository

  @Binds
  fun bindNovelRepository(repository: NovelRepositoryImpl): NovelRepository

  @Binds
  fun bindIndexRepository(repository: IndexRepositoryImpl): IndexRepository

  @Binds
  fun bindRankingRepository(repository: RankingRepositoryImpl): RankingRepository
}
