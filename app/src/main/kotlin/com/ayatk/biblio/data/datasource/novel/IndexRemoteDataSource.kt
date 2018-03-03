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

package com.ayatk.biblio.data.datasource.novel

import com.ayatk.biblio.data.remote.NarouClient
import com.ayatk.biblio.data.remote.entity.mapper.toIndex
import com.ayatk.biblio.data.repository.IndexRepository
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class IndexRemoteDataSource @Inject constructor(
    val client: NarouClient
) : IndexRepository {

  override fun findAll(novel: Novel): Single<List<Index>> {
    return client.getTableOfContents(novel.code)
        .map { it.toIndex(novel) }
  }

  override fun save(indices: List<Index>): Completable = Completable.create { /* no-op */ }

  override fun delete(novel: Novel): Completable = Completable.create { /* no-op */ }
}
