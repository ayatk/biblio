/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import com.ayatk.biblio.repository.library.LibraryDataSource
import com.ayatk.biblio.repository.library.LibraryLocalDataSource
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.repository.novel.NovelBodyDataSource
import com.ayatk.biblio.repository.novel.NovelBodyLocalDataSource
import com.ayatk.biblio.repository.novel.NovelBodyRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelBodyRepository
import com.ayatk.biblio.repository.novel.NovelDataSource
import com.ayatk.biblio.repository.novel.NovelLocalDataSource
import com.ayatk.biblio.repository.novel.NovelRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelRepository
import com.ayatk.biblio.repository.novel.NovelTableDataSource
import com.ayatk.biblio.repository.novel.NovelTableLocalDataSource
import com.ayatk.biblio.repository.novel.NovelTableRemoteDataSource
import com.ayatk.biblio.repository.novel.NovelTableRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun provideLibraryRepository(localDataSource: LibraryLocalDataSource): LibraryDataSource
      = LibraryRepository(localDataSource)

  @Singleton
  @Provides
  fun provideNovelBodyRepository(
      local: NovelBodyLocalDataSource,
      remote: NovelBodyRemoteDataSource
  ): NovelBodyDataSource = NovelBodyRepository(local, remote)

  @Singleton
  @Provides
  fun provideNovelRepository(
      local: NovelLocalDataSource,
      remote: NovelRemoteDataSource
  ): NovelDataSource = NovelRepository(local, remote)

  @Singleton
  @Provides
  fun provideNovelTableRepository(
      local: NovelTableLocalDataSource,
      remote: NovelTableRemoteDataSource
  ): NovelTableDataSource = NovelTableRepository(local, remote)
}
