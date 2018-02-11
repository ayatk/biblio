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

package com.ayatk.biblio.data.datasource.novel

import com.ayatk.biblio.domain.repository.NovelBodyRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import com.ayatk.biblio.util.rx.SchedulerProvider
import com.ayatk.biblio.util.toSingle
import io.reactivex.Completable
import io.reactivex.Single

class NovelBodyDataSource(
    private val localDataSource: NovelBodyLocalDataSource,
    private val remoteDataSource: NovelBodyRemoteDataSource,
    private val schedulerProvider: SchedulerProvider
) : NovelBodyRepository {

  override fun find(novel: Novel, page: Int): Single<List<NovelBody>> =
      localDataSource.find(novel, page)
          .flatMap {
            if (it.isEmpty()) {
              return@flatMap findToRemote(novel, page)
            }
            return@flatMap it.toSingle()
          }
          .subscribeOn(schedulerProvider.io())

  override fun save(novelBody: NovelBody): Completable =
      localDataSource.save(novelBody)
          .subscribeOn(schedulerProvider.io())

  override fun deleteAll(novel: Novel): Single<Int> =
      localDataSource.deleteAll(novel)
          .subscribeOn(schedulerProvider.io())

  private fun findToRemote(novel: Novel, page: Int): Single<List<NovelBody>> =
      remoteDataSource.find(novel, page)
          .doOnSuccess { save(it.first()).subscribe() }
          .subscribeOn(schedulerProvider.io())
}
