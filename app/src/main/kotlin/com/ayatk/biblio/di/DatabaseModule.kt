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

import android.app.Application
import android.arch.persistence.room.Room
import com.ayatk.biblio.data.db.AppDatabase
import com.ayatk.biblio.data.db.EpisodeDatabase
import com.ayatk.biblio.data.db.EpisodeDatabaseImpl
import com.ayatk.biblio.data.db.IndexDatabase
import com.ayatk.biblio.data.db.IndexDatabaseImpl
import com.ayatk.biblio.data.db.LibraryDatabase
import com.ayatk.biblio.data.db.LibraryDatabaseImpl
import com.ayatk.biblio.data.db.NovelDatabase
import com.ayatk.biblio.data.db.NovelDatabaseImpl
import com.ayatk.biblio.model.OrmaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

  @Singleton
  @Provides
  fun provideEpisodeDatabase(
      orma: OrmaDatabase
  ): EpisodeDatabase = EpisodeDatabaseImpl(orma)

  @Singleton
  @Provides
  fun provideIndexDatabase(
      orma: OrmaDatabase
  ): IndexDatabase = IndexDatabaseImpl(orma)

  @Singleton
  @Provides
  fun provideLibraryDatabase(
      orma: OrmaDatabase
  ): LibraryDatabase = LibraryDatabaseImpl(orma)

  @Singleton
  @Provides
  fun provideNovelDatabase(
      orma: OrmaDatabase
  ): NovelDatabase = NovelDatabaseImpl(orma)
}
