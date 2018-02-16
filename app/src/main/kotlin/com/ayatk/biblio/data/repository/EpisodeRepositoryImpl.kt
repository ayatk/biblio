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
import com.ayatk.biblio.data.db.EpisodeDatabase
import com.ayatk.biblio.model.Episode
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.toSingle
import io.reactivex.Completable
import io.reactivex.Single

class EpisodeRepositoryImpl(
    private val database: EpisodeDatabase,
    private val remoteDataSource: EpisodeRemoteDataSource
) : EpisodeRepository {

  override fun find(novel: Novel, page: Int): Single<List<Episode>> =
      database.find(novel, page)
          .flatMap {
            if (it.isEmpty()) {
              return@flatMap findToRemote(novel, page)
            }
            return@flatMap it.toSingle()
          }

  override fun save(episode: Episode): Completable = database.save(episode)

  override fun deleteAll(novel: Novel): Completable = database.delete(novel)

  private fun findToRemote(novel: Novel, page: Int): Single<List<Episode>> =
      remoteDataSource.find(novel, page)
          .doOnSuccess { save(it.first()).subscribe() }
}
