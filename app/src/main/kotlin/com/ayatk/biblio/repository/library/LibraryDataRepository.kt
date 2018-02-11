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
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class LibraryDataRepository(private val localDataSource: LibraryLocalDataSource) : LibraryDataSource {

  private var cachedLibrary = emptyMap<String, Library>()

  override fun findAll(): Single<MutableList<Library>> {
    return localDataSource.findAll()
  }

  override fun find(novel: Novel): Maybe<Library> {
    return localDataSource.find(novel)
  }

  override fun save(library: Library): Completable {
    cachedLibrary.plus(Pair(library.id, library))
    return localDataSource.save(library)
  }

  override fun saveAll(libraries: List<Library>): Completable {
    libraries.forEach { library -> cachedLibrary.plus(Pair(library.id, library)) }
    return localDataSource.saveAll(libraries)
  }

  override fun updateAllAsync(novels: List<Novel>) {
    localDataSource.updateAllAsync(novels)
  }

  override fun delete(novel: Novel): Single<Int> {
    return localDataSource.delete(novel)
  }
}
