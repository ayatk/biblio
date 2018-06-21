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

package com.ayatk.biblio.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayatk.biblio.data.entity.EpisodeEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface EpisodeDao {

  @Query("SELECT * FROM episode WHERE novel_code = :code")
  fun getAllEpisodeByCode(code: String): Flowable<List<EpisodeEntity>>

  @Query("SELECT * FROM episode WHERE novel_code = :code AND page = :page LIMIT 1")
  fun findEpisodeByCodeAndPage(code: String, page: Int): Maybe<EpisodeEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(episode: EpisodeEntity)

  @Delete
  fun delete(episode: EpisodeEntity)
}
