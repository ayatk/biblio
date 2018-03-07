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

package com.ayatk.biblio.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.ayatk.biblio.data.db.dao.BookmarkDao
import com.ayatk.biblio.data.db.dao.EpisodeDao
import com.ayatk.biblio.data.db.dao.IndexDao
import com.ayatk.biblio.data.db.dao.NovelDao
import com.ayatk.biblio.data.entity.BookmarkEntity
import com.ayatk.biblio.data.entity.EpisodeEntity
import com.ayatk.biblio.data.entity.IndexEntity
import com.ayatk.biblio.data.entity.NovelEntity

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
