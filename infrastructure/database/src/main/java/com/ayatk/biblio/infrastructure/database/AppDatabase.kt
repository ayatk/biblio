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

package com.ayatk.biblio.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ayatk.biblio.infrastructure.database.dao.BookmarkDao
import com.ayatk.biblio.infrastructure.database.dao.EpisodeDao
import com.ayatk.biblio.infrastructure.database.dao.IndexDao
import com.ayatk.biblio.infrastructure.database.dao.NovelDao
import com.ayatk.biblio.infrastructure.database.entity.BookmarkEntity
import com.ayatk.biblio.infrastructure.database.entity.EpisodeEntity
import com.ayatk.biblio.infrastructure.database.entity.IndexEntity
import com.ayatk.biblio.infrastructure.database.entity.NovelEntity

@Database(
  entities = [
    BookmarkEntity::class,
    EpisodeEntity::class,
    IndexEntity::class,
    NovelEntity::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun bookmarkDao(): BookmarkDao

  abstract fun episodeDao(): EpisodeDao

  abstract fun indexDao(): IndexDao

  abstract fun novelDao(): NovelDao
}
