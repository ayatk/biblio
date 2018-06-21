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
import androidx.room.Room
import com.ayatk.biblio.data.db.AppDatabase
import com.ayatk.biblio.data.db.dao.BookmarkDao
import com.ayatk.biblio.data.db.dao.EpisodeDao
import com.ayatk.biblio.data.db.dao.IndexDao
import com.ayatk.biblio.data.db.dao.NovelDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

  @Singleton
  @Provides
  fun provideDb(app: Application): AppDatabase =
      Room.databaseBuilder(app, AppDatabase::class.java, "biblio.db")
          .fallbackToDestructiveMigration()
          .build()

  @Singleton
  @Provides
  fun provideBookmarkDao(db: AppDatabase): BookmarkDao = db.bookmarkDao()

  @Singleton
  @Provides
  fun provideEpisodeDao(db: AppDatabase): EpisodeDao = db.episodeDao()

  @Singleton
  @Provides
  fun provideIndexDao(db: AppDatabase): IndexDao = db.indexDao()

  @Singleton
  @Provides
  fun provideNovelDao(db: AppDatabase): NovelDao = db.novelDao()
}
