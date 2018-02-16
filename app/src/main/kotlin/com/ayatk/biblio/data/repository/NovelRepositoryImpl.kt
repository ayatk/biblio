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

import com.ayatk.biblio.data.datasource.novel.NovelRemoteDataSource
import com.ayatk.biblio.data.db.NovelDatabase
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.util.rx.toSingle
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NovelRepositoryImpl @Inject constructor(
    private val database: NovelDatabase,
    private val remoteDataSource: NovelRemoteDataSource
) : NovelRepository {

  var isDirty = false

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return if (isDirty) {
      findAllFromRemote(codes, publisher)
    } else {
      findAllFromLocal(codes, publisher)
    }
  }

  override fun save(novel: Novel): Completable = database.save(novel)

  override fun delete(code: String) {
    database.delete(code)
  }

  private fun findAllFromRemote(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return remoteDataSource.findAll(codes, publisher).doOnSuccess(::updateAllAsync)
  }

  private fun updateAllAsync(novels: List<Novel>) {
    novels.forEach { novel -> database.save(novel).subscribe() }
  }

  private fun findAllFromLocal(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return database.find(publisher, *codes.toTypedArray())
        .flatMap { novels ->
          if (novels.isEmpty()) {
            findAllFromRemote(codes, publisher)
          } else {
            novels.toSingle()
          }
        }
  }
}
