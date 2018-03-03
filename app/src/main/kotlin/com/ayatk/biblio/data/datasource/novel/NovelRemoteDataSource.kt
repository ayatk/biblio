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
import com.ayatk.biblio.data.remote.util.QueryBuilder
import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelRemoteDataSource @Inject constructor(
    private val client: NarouClient
) : NovelRepository {

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    if (codes.isEmpty()) {
      return Single.create { listOf<Novel>() }
    }
    return when (publisher) {
      Publisher.NAROU -> {
        client
            .getNovel(QueryBuilder().ncode(*codes.toTypedArray()).build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
      }
      Publisher.NOCTURNE_MOONLIGHT -> {
        client
            .getNovel18(QueryBuilder().ncode(*codes.toTypedArray()).build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
      }
    }
  }

  override fun save(novel: Novel): Completable = Completable.create {}

  override fun delete(code: String) {}
}
