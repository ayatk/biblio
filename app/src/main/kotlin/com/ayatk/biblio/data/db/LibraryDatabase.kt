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

import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import com.github.gfx.android.orma.annotation.OnConflict
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject

class LibraryDatabase @Inject constructor(
    private val orma: OrmaDatabase
) : LibraryRepository {

  override fun findAll(): Flowable<List<Library>> {
    return orma.selectFromLibrary()
        .executeAsObservable()
        .toList()
        .toFlowable()
  }

  override fun find(novel: Novel): Maybe<Library> {
    return orma.selectFromLibrary()
        .novelEq(novel)
        .executeAsObservable()
        .firstElement()
  }

  override fun save(library: Library): Completable =
      orma.transactionAsCompletable {
        if (orma.relationOfNovel().codeEq(library.novel.code).isEmpty) {
          orma.relationOfNovel().inserter().execute(library.novel)
        }
        orma.relationOfLibrary().inserter(OnConflict.REPLACE).execute(library)
      }

  override fun saveAll(libraries: List<Library>): Completable =
      orma.transactionAsCompletable {
        libraries.forEach { library ->
          if (orma.relationOfNovel().selector().codeEq(library.novel.code).isEmpty) {
            orma.relationOfNovel().inserter().execute(library.novel)
          }
          orma.relationOfLibrary().inserter(OnConflict.REPLACE).execute(library)
        }
      }

  override fun updateAllAsync(novels: List<Novel>): Completable =
      orma.transactionAsCompletable {
        novels.forEach { orma.relationOfLibrary().upsert(Library(novel = it)) }
      }

  override fun delete(id: Long): Completable {
    return orma.relationOfLibrary()
        .deleter()
        .idEq(id)
        .executeAsSingle()
        .toCompletable()
  }
}
