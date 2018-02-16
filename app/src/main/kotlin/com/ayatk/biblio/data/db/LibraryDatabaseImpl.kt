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

import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import com.github.gfx.android.orma.annotation.OnConflict
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class LibraryDatabaseImpl @Inject constructor(
    private val orma: OrmaDatabase
) : LibraryDatabase {

  override fun findAll(): Single<List<Library>> =
      orma.selectFromLibrary()
          .executeAsObservable()
          .toList()

  override fun find(novel: Novel): Maybe<Library> =
      orma.selectFromLibrary()
          .novelEq(novel)
          .executeAsObservable()
          .firstElement()

  override fun save(novel: Novel): Completable =
      orma.transactionAsCompletable {
        if (orma.relationOfNovel().codeEq(novel.code).isEmpty) {
          orma.relationOfNovel().inserter().execute(novel)
        }
        orma.relationOfLibrary().inserter(OnConflict.REPLACE).execute(Library(novel = novel))
      }

  override fun saveAll(novels: List<Novel>): Completable =
      orma.transactionAsCompletable {
        novels.forEach { novel ->
          if (orma.relationOfNovel().codeEq(novel.code).isEmpty) {
            orma.relationOfNovel().inserter().execute(novel)
          }
          orma.relationOfLibrary().inserter(OnConflict.REPLACE).execute(Library(novel = novel))
        }
      }

  override fun update(library: Library): Completable =
      orma.transactionAsCompletable {
        orma.updateLibrary()
            .idEq(library.id)
            .novel(library.novel)
            .tag(library.tag)
      }

  override fun delete(id: Long): Completable =
      orma.relationOfLibrary()
          .deleter()
          .idEq(id)
          .executeAsSingle()
          .toCompletable()
}
