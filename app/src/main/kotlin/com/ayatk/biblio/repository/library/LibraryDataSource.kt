/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.library

import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LibraryDataSource {

  fun findAll(): Single<MutableList<Library>>

  fun find(novel: Novel): Maybe<Library>

  fun save(library: Library): Completable

  fun saveAll(libraries: List<Library>): Completable

  fun updateAllAsync(novels: List<Novel>)

  fun delete(novel: Novel): Single<Int>
}
