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

import android.support.annotation.CheckResult
import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class HomeLibraryUseCaseImpl @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val schedulerProvider: SchedulerProvider
) : HomeLibraryUseCase {

  override val libraries: Flowable<List<Library>> =
      libraryRepository
          .findAll()
          .map { it.sortedByDescending { it.novel.lastUpdateDate } } // 日付順でそーと
          .subscribeOn(schedulerProvider.io())

//  @CheckResult
//  override fun refresh(): Completable =
//      libraryRepository.findAll()
//          .flatMapCompletable {
//
//            libraryRepository.updateAllAsync(it.map { it.novel })
//          }

  @CheckResult
  override fun delete(id: Long): Completable =
      libraryRepository
          .delete(id)
          .subscribeOn(schedulerProvider.io())
}
