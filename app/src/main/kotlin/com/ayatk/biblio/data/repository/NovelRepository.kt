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

import com.ayatk.biblio.data.entity.NovelEntity
import com.ayatk.biblio.data.entity.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Flowable

interface NovelRepository {

  val savedNovels: Flowable<List<NovelEntity>>

  fun novels(publisher: Publisher, vararg codes: String): Flowable<List<NovelEntity>>

  fun save(novel: NovelEntity): Completable

  fun delete(novel: NovelEntity): Completable
}
