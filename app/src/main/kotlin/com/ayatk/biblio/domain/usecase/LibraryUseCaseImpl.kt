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

package com.ayatk.biblio.domain.usecase

import com.ayatk.biblio.data.repository.IndexRepository
import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.domain.translator.toEntity
import com.ayatk.biblio.domain.translator.toLibrary
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryUseCaseImpl @Inject constructor(
  private val novelRepository: NovelRepository,
  private val indexRepository: IndexRepository,
  private val schedulerProvider: SchedulerProvider
) : LibraryUseCase {

  override val libraries: Flowable<List<Library>> =
    novelRepository.savedNovels
      .map { it.map { it.toLibrary() } }
      .subscribeOn(schedulerProvider.io())

  override fun update(): Completable =
    Completable.fromCallable {
      // TODO: 更新処理
    }
      .subscribeOn(schedulerProvider.io())

  override fun delete(novel: Novel): Completable =
    novelRepository.delete(novel.toEntity())
      .subscribeOn(schedulerProvider.io())
}
