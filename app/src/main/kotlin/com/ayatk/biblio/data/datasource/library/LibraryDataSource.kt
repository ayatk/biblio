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

package com.ayatk.biblio.data.datasource.library

import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class LibraryDataSource @Inject constructor(
    private val localDataSource: LibraryLocalDataSource,
    private val schedulerProvider: SchedulerProvider
) : LibraryRepository {

  private var cachedLibrary = emptyMap<String, Library>()

  override fun findAll(): Single<MutableList<Library>> =
      localDataSource.findAll()
          .subscribeOn(schedulerProvider.io())

  override fun find(novel: Novel): Maybe<Library> =
      localDataSource.find(novel)
          .subscribeOn(schedulerProvider.io())

  override fun save(library: Library): Completable {
    cachedLibrary.plus(Pair(library.id, library))
    return localDataSource.save(library)
        .subscribeOn(schedulerProvider.io())
  }

  override fun saveAll(libraries: List<Library>): Completable {
    libraries.forEach { library -> cachedLibrary.plus(Pair(library.id, library)) }
    return localDataSource.saveAll(libraries)
        .subscribeOn(schedulerProvider.io())
  }

  override fun updateAllAsync(novels: List<Novel>) {
    localDataSource.updateAllAsync(novels)
  }

  override fun delete(novel: Novel): Single<Int> =
      localDataSource.delete(novel)
          .subscribeOn(schedulerProvider.io())
}
