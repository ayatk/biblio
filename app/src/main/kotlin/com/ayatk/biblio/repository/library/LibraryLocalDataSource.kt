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

package com.ayatk.biblio.repository.library

import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import com.github.gfx.android.orma.annotation.OnConflict
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LibraryLocalDataSource
@Inject constructor(private val orma: OrmaDatabase) : LibraryRepository {

  override fun findAll(): Single<MutableList<Library>> {
    return orma.selectFromLibrary()
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun find(novel: Novel): Maybe<Library> {
    return orma.selectFromLibrary()
        .novelEq(novel)
        .executeAsObservable()
        .firstElement()
        .subscribeOn(Schedulers.io())
  }

  override fun save(library: Library): Completable {
    return orma.transactionAsCompletable {
      if (orma.relationOfNovel().codeEq(library.novel.code).isEmpty) {
        orma.relationOfNovel().inserter().execute(library.novel)
      }
      orma.relationOfLibrary().inserter(OnConflict.REPLACE).execute(library)
    }.subscribeOn(Schedulers.io())
  }

  override fun saveAll(libraries: List<Library>): Completable {
    return orma.transactionAsCompletable {
      libraries.forEach { library ->
        if (orma.relationOfNovel().selector().codeEq(library.novel.code).isEmpty) {
          orma.relationOfNovel().inserter().execute(library.novel)
        }
        orma.relationOfLibrary().inserter(OnConflict.REPLACE).execute(library)
      }
    }.subscribeOn(Schedulers.io())
  }

  override fun updateAllAsync(novels: List<Novel>) {
    orma.transactionAsCompletable {
      novels.forEach { orma.relationOfLibrary().upsert(Library(novel = it)) }
    }
        .subscribeOn(Schedulers.io())
        .subscribe()
  }

  override fun delete(novel: Novel): Single<Int> {
    return orma.relationOfLibrary()
        .deleter()
        .novelEq(novel)
        .executeAsSingle()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }
}
