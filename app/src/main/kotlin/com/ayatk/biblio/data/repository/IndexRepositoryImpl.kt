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

import com.ayatk.biblio.data.remote.NarouDataStore
import com.ayatk.biblio.data.remote.entity.mapper.toEntity
import com.ayatk.biblio.di.scope.Narou
import com.ayatk.biblio.di.scope.Nocturne
import com.ayatk.biblio.infrastructure.database.dao.IndexDao
import com.ayatk.biblio.infrastructure.database.entity.IndexEntity
import com.ayatk.biblio.infrastructure.database.entity.NovelEntity
import com.ayatk.biblio.infrastructure.database.entity.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndexRepositoryImpl @Inject constructor(
  private val dao: IndexDao,
  @Narou private val narouDataStore: NarouDataStore,
  @Nocturne private val nocDataStore: NarouDataStore
) : IndexRepository {

  override fun index(entity: NovelEntity): Flowable<List<IndexEntity>> =
    Flowables.combineLatest(
      when (entity.publisher) {
        Publisher.NAROU -> narouDataStore.getIndex(entity.code)
        Publisher.NOCTURNE_MOONLIGHT -> nocDataStore.getIndex(entity.code)
      }
        .map { it.toEntity() },
      dao.getAllIndexByCode(entity.code)
    ) { remote, local ->
      if (remote.isEmpty()) local else remote
    }

  override fun save(indexes: List<IndexEntity>): Completable =
    Completable.fromRunnable { dao.insert(indexes) }

  override fun delete(code: String): Completable =
    Completable.fromRunnable { dao.getAllIndexByCode(code) }
}
