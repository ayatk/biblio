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

package com.ayatk.biblio.data.db

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NovelDatabaseImpl @Inject constructor(
    private val orma: OrmaDatabase
) : NovelDatabase {

  override fun findAll(vararg publishers: Publisher): Single<List<Novel>> =
      orma.selectFromNovel()
          .publisherIn(*publishers)
          .executeAsObservable()
          .toList()

  override fun find(publisher: Publisher, vararg codes: String): Single<List<Novel>> =
      orma.selectFromNovel()
          .codeIn(*codes)
          .publisherEq(publisher)
          .executeAsObservable()
          .toList()

  override fun save(vararg novels: Novel): Completable =
      orma.transactionAsCompletable {
        novels.map { novel ->
          if (orma.relationOfNovel().codeEq(novel.code).isEmpty) {
            orma.relationOfNovel().inserter().execute(novel)
          }
        }
      }

  override fun update(vararg novels: Novel): Completable =
      orma.transactionAsCompletable {
        novels.map { novel ->
          orma.relationOfNovel().upsert(novel)
        }
      }

  override fun delete(vararg codes: String): Completable =
      orma.transactionAsCompletable {
        orma.relationOfNovel()
            .deleter()
            .codeIn(*codes)
            .execute()
      }
}
