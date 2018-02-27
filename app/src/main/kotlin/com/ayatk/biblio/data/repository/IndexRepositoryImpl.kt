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
import com.ayatk.biblio.data.db.dao.IndexDao
import com.ayatk.biblio.data.entity.IndexEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndexRepositoryImpl @Inject constructor(
    private val dao: IndexDao,
    private val remoteDataSource: IndexRemoteDataSource
) : IndexRepository {

  override fun indexes(code: String): Flowable<List<IndexEntity>> =
      dao.getAllIndexByCode(code)

  override fun save(indices: List<IndexEntity>): Completable =
      Completable.fromRunnable {
        indices.map(dao::insert)
      }

  override fun delete(code: String): Completable =
      Completable.fromRunnable {
        indexes(code).map { it.map(dao::delete) }
      }
}
