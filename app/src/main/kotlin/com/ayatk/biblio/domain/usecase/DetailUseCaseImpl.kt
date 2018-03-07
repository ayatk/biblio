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
import com.ayatk.biblio.domain.translator.toModel
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.SchedulerProvider
import com.ayatk.biblio.util.rx.toSingle
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class DetailUseCaseImpl @Inject constructor(
    private val novelRepository: NovelRepository,
    private val indexRepository: IndexRepository,
    private val schedulerProvider: SchedulerProvider
) : DetailUseCase {

  override fun getLibrary(novel: Novel): Single<Library> =
      // TODO とりあえず今はタグ実装してないのでそのまま変換して流してる
      Library("remote", novel, emptyList()).toSingle()

  override fun saveLibrary(library: Library): Completable {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getIndex(novel: Novel): Flowable<List<Index>> =
      indexRepository.index(novel.toEntity())
          .map { it.toModel(novel) }
          .subscribeOn(schedulerProvider.io())

  override fun updateIndex(novel: Novel): Completable {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
