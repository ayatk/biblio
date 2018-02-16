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

import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface LibraryRepository {

  fun findAll(): Flowable<List<Library>>

  fun find(novel: Novel): Maybe<Library>

  fun save(library: Library): Completable

  fun saveAll(libraries: List<Library>): Completable

  fun updateAllAsync(novels: List<Novel>): Completable

  fun delete(id: Long): Completable
}
