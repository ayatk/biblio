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

import com.ayatk.biblio.infrastructure.database.dao.NovelDao
import com.ayatk.biblio.infrastructure.database.entity.NovelEntity
import com.ayatk.biblio.infrastructure.database.entity.enums.Publisher
import com.ayatk.biblio.data.remote.NarouDataStore
import com.ayatk.biblio.data.remote.entity.mapper.toEntity
import com.ayatk.biblio.data.remote.util.QueryBuilder
import com.ayatk.biblio.di.scope.Narou
import com.ayatk.biblio.di.scope.Nocturne
import io.reactivex.Completable
import io.reactivex.Flowable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NovelRepositoryImpl @Inject constructor(
  private val dao: NovelDao,
  @Narou private val narouDataStore: NarouDataStore,
  @Nocturne private val nocDataStore: NarouDataStore
) : NovelRepository {

  override val savedNovels: Flowable<List<NovelEntity>> = dao.getAllNovel()

  override fun novels(publisher: Publisher, vararg codes: String): Flowable<List<NovelEntity>> {
    // TODO データベースからとってくる処理をかく
    val query = QueryBuilder().ncode(*codes).build()
    return when (publisher) {
      Publisher.NAROU -> narouDataStore.getNovel(query)
      Publisher.NOCTURNE_MOONLIGHT -> nocDataStore.getNovel(query)
    }
      .map { it.toEntity(publisher) }
  }

  override fun novelsByQuery(rawQuery: String, publisher: Publisher): Flowable<List<NovelEntity>> {
    // TODO: フィルタリングなんかの条件をどうするか後で考える
    val query = QueryBuilder().searchWords(rawQuery).build()
    return when (publisher) {
      Publisher.NAROU -> narouDataStore.getNovel(query)
      Publisher.NOCTURNE_MOONLIGHT -> nocDataStore.getNovel(query)
    }
      .map { it.toEntity(publisher) }
  }

  override fun save(novel: NovelEntity): Completable =
    Completable.fromRunnable {
      Timber.d("save ${novel.title}")
      dao.insert(novel)
    }

  override fun delete(novel: NovelEntity): Completable =
    Completable.fromRunnable {
      dao.delete(novel)
    }
}
