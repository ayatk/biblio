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

import com.ayatk.biblio.data.datasource.novel.EpisodeRemoteDataSource
import com.ayatk.biblio.data.db.dao.EpisodeDao
import com.ayatk.biblio.data.entity.EpisodeEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepositoryImpl @Inject constructor(
    private val dao: EpisodeDao,
    private val remoteDataSource: EpisodeRemoteDataSource
) : EpisodeRepository {

  override fun find(code: String, page: Int): Single<EpisodeEntity> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun save(episode: EpisodeEntity): Completable =
      Completable.fromCallable { dao::insert }

  override fun deleteAll(code: String): Completable =
      Completable.fromRunnable {
        dao.getAllEpisodeByCode(code)
            .map { it.map(dao::delete) }
      }
}