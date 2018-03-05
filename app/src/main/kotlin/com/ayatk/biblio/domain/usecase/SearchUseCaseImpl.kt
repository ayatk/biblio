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

import com.ayatk.biblio.data.repository.NovelRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val repository: NovelRepository,
    private val schedulerProvider: SchedulerProvider
) : SearchUseCase {

  override fun search(query: String): Flowable<Map<Novel, Boolean>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun saveNovel(novel: Novel): Completable {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
