/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.library

import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class LibraryRepository(private val localDataSource: LibraryLocalDataSource) : LibraryDataSource {

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
