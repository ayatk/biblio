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

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle

class NovelBodyRepository(
    private val localDataSource: NovelBodyLocalDataSource,
    private val remoteDataSource: NovelBodyRemoteDataSource
) : NovelBodyDataSource {

  override fun find(novel: Novel, page: Int): Single<List<NovelBody>> {
    return localDataSource.find(novel, page)
        .flatMap {
          if (it.isEmpty()) {
            return@flatMap findToRemote(novel, page)
          }
          return@flatMap it.toSingle()
        }
  }

  override fun save(novelBody: NovelBody): Completable {
    return localDataSource.save(novelBody)
  }

  override fun deleteAll(novel: Novel): Single<Int> {
    return localDataSource.deleteAll(novel)
  }

  private fun findToRemote(novel: Novel, page: Int): Single<List<NovelBody>> {
    return remoteDataSource.find(novel, page)
        .doOnSuccess { save(it.first()).subscribe() }
  }
}
