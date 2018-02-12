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

import com.ayatk.biblio.domain.repository.IndexRepository
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IndexLocalDataSource
@Inject constructor(val orma: OrmaDatabase) : IndexRepository {

  override fun findAll(novel: Novel): Single<List<Index>> {
    return orma.selectFromIndex()
        .novelEq(novel)
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun find(novel: Novel, page: Int): Maybe<Index> {
    return orma.selectFromIndex()
        .novelEq(novel)
        .page(page.toLong())
        .executeAsObservable()
        .firstElement()
        .subscribeOn(Schedulers.io())
  }

  override fun save(indices: List<Index>): Completable {
    return orma.transactionAsCompletable {
      indices.map {
        orma.relationOfIndex().upsert(it)
      }
    }.subscribeOn(Schedulers.io())
  }

  override fun delete(novel: Novel): Single<Int> {
    return orma.relationOfIndex()
        .deleter()
        .novelEq(novel)
        .executeAsSingle()
        .subscribeOn(Schedulers.io())
  }
}
