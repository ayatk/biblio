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

import com.ayatk.biblio.infrastructure.database.dao.BookmarkDao
import com.ayatk.biblio.infrastructure.database.entity.BookmarkEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkRepositoryImpl @Inject constructor(
  private val dao: BookmarkDao
) : BookmarkRepository {

  override val bookmarks: Flowable<List<BookmarkEntity>> = dao.getAllBookmark()

  override fun save(bookmarks: List<BookmarkEntity>): Completable =
    Completable.fromRunnable { dao::insert }

  override fun delete(id: UUID): Completable =
    dao.getAllBookmark()
      .map { bookmarks -> bookmarks.first { it.id == id } } // 基本マッチしないIDはない設計
      .flatMapCompletable {
        Completable.fromRunnable { dao::delete }
      }
}
