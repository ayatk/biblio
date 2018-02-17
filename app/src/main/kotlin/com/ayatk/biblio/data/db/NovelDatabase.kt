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

import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class NovelDatabase @Inject constructor(
    private val orma: OrmaDatabase
) : NovelRepository {

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return orma.selectFromNovel()
        .publisherEq(publisher)
        .executeAsObservable()
        .toList()
  }

  override fun find(code: String, publisher: Publisher): Maybe<Novel> {
    return orma.selectFromNovel()
        .codeEq(code)
        .publisherEq(publisher)
        .executeAsObservable()
        .firstElement()
  }

  override fun save(novel: Novel): Completable {
    return orma.transactionAsCompletable {
      orma.relationOfNovel().upsert(novel)
    }
  }

  override fun delete(code: String) {
    orma.relationOfNovel()
        .deleter()
        .codeEq(code)
        .execute()
  }
}
