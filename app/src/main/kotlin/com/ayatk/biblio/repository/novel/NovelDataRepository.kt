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

import android.support.annotation.VisibleForTesting
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.util.toMaybe
import com.ayatk.biblio.util.toSingle
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.LinkedHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NovelDataRepository @Inject constructor(
    private val localDataSource: NovelLocalDataSource,
    private val remoteDataSource: NovelRemoteDataSource
) : NovelRepository {

  @VisibleForTesting
  private var cache: MutableMap<String, Novel> = LinkedHashMap()

  var isDirty = false

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    if (hasCache() && !isDirty) {
      return ArrayList(cache.values).toSingle()
    }

    return if (isDirty) findAllFromRemote(codes, publisher) else findAllFromLocal(codes, publisher)
  }

  override fun find(code: String, publisher: Publisher): Maybe<Novel> {
    if (hasCache(code)) {
      return cache[code].toMaybe()
    }

    return if (isDirty) {
      remoteDataSource.find(code, publisher)
          .doOnSuccess { novel -> updateAllAsync(listOf(novel)) }
    } else {
      localDataSource.find(code, publisher)
    }
  }

  override fun save(novel: Novel): Completable {
    cache.put(novel.code, novel)
    return localDataSource.save(novel)
  }

  override fun delete(code: String) {
    localDataSource.delete(code)
  }

  private fun hasCache(): Boolean = !cache.isEmpty()

  private fun hasCache(code: String): Boolean = cache.containsKey(code)

  private fun findAllFromRemote(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return remoteDataSource.findAll(codes, publisher)
        .doOnSuccess(
            { novels ->
              refreshCache(novels)
              updateAllAsync(novels)
            }
        )
  }

  private fun updateAllAsync(novels: List<Novel>) {
    novels.forEach { novel -> localDataSource.save(novel).subscribe() }
  }

  private fun findAllFromLocal(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return localDataSource.findAll(codes, publisher)
        .flatMap { novels ->
          if (novels.isEmpty()) {
            return@flatMap findAllFromRemote(codes, publisher)
          }
          refreshCache(novels)
          return@flatMap novels.toSingle()
        }
  }

  private fun refreshCache(novels: List<Novel>) {
    cache.clear()
    novels.forEach { novel -> cache.put(novel.code, novel) }
    isDirty = false
  }
}
