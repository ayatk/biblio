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

package com.ayatk.biblio.data.repository

import com.ayatk.biblio.data.db.dao.EpisodeDao
import com.ayatk.biblio.data.entity.EpisodeEntity
import com.ayatk.biblio.data.entity.NovelEntity
import com.ayatk.biblio.data.entity.enums.NovelState
import com.ayatk.biblio.data.remote.NarouDataStore
import com.ayatk.biblio.data.remote.entity.mapper.toEntity
import com.ayatk.biblio.di.scope.Narou
import com.ayatk.biblio.di.scope.Nocturne
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepositoryImpl @Inject constructor(
  private val dao: EpisodeDao,
  @Narou private val narouDataStore: NarouDataStore,
  @Nocturne private val nocDataStore: NarouDataStore
) : EpisodeRepository {

  override fun find(entity: NovelEntity, page: Int): Flowable<EpisodeEntity> =
    if (entity.novelState == NovelState.SHORT_STORY) {
      narouDataStore.getShortStory(entity.code)
    } else {
      narouDataStore.getEpisode(entity.code, page)
    }
      .map { it.toEntity() }

  override fun save(episode: EpisodeEntity): Completable =
    Completable.fromCallable { dao::insert }

  override fun deleteAll(code: String): Completable =
    Completable.fromRunnable {
      dao.getAllEpisodeByCode(code)
        .map { it.map(dao::delete) }
    }
}
