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

import com.ayatk.biblio.data.datasource.novel.IndexRemoteDataSource
import com.ayatk.biblio.data.db.IndexDatabase
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.toSingle
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndexDataSource @Inject constructor(
    private val database: IndexDatabase,
    private val remoteDataSource: IndexRemoteDataSource
) : IndexRepository {

  var isDirty = false

  override fun findAll(novel: Novel): Single<List<Index>> =
      if (isDirty) findAllFromRemote(novel) else findAllFromLocal(novel)

  override fun find(novel: Novel, page: Int): Maybe<Index> = database.find(novel, page)

  override fun save(indices: List<Index>): Completable = database.save(indices)

  override fun delete(novel: Novel): Single<Int> = database.delete(novel)

  private fun findAllFromRemote(novel: Novel): Single<List<Index>> =
      remoteDataSource.findAll(novel)
          .doOnSuccess(this::updateAllAsync)

  private fun findAllFromLocal(novel: Novel): Single<List<Index>> =
      database.findAll(novel)
          .flatMap {
            if (it.isEmpty()) {
              return@flatMap findAllFromRemote(novel)
            }
            return@flatMap it.toSingle()
          }

  private fun updateAllAsync(indices: List<Index>) {
    database.save(indices).subscribe()
    isDirty = false
  }
}
