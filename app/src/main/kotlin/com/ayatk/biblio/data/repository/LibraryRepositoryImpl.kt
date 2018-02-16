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

package com.ayatk.biblio.data.repository

import com.ayatk.biblio.data.db.LibraryDatabase
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val database: LibraryDatabase
) : LibraryRepository {

  override fun findAll(): Single<List<Library>> = database.findAll()

  override fun find(novel: Novel): Maybe<Library> = database.find(novel)

  override fun save(novel: Novel): Completable = database.save(novel)

  override fun saveAll(novels: List<Novel>): Completable = database.saveAll(novels)

  override fun update(libraries: List<Library>): Completable =
      Completable.merge(libraries.map(database::update))

  override fun delete(id: Long): Completable = database.delete(id)
}
