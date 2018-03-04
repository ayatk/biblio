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

package com.ayatk.biblio.ui.home.ranking

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ayatk.biblio.domain.usecase.TopRankingUseCase
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.model.enums.RankingType
import com.ayatk.biblio.ui.util.toResult
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.toLiveData
import com.ayatk.biblio.util.rx.SchedulerProvider
import javax.inject.Inject

class TopRankingViewModel @Inject constructor(
    private val useCase: TopRankingUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  val daily: LiveData<Result<List<Ranking>>> by lazy {
    useCase.ranking(Publisher.NAROU, RankingType.DAILY)
        .toResult(schedulerProvider)
        .toLiveData()
  }

  val weekly: LiveData<Result<List<Ranking>>> by lazy {
    useCase.ranking(Publisher.NAROU, RankingType.WEEKLY)
        .toResult(schedulerProvider)
        .toLiveData()
  }

  val monthly: LiveData<Result<List<Ranking>>> by lazy {
    useCase.ranking(Publisher.NAROU, RankingType.MONTHLY)
        .toResult(schedulerProvider)
        .toLiveData()
  }

  val quarter: LiveData<Result<List<Ranking>>> by lazy {
    useCase.ranking(Publisher.NAROU, RankingType.QUARTET)
        .toResult(schedulerProvider)
        .toLiveData()
  }

  val all: LiveData<Result<List<Ranking>>> by lazy {
    useCase.ranking(Publisher.NAROU, RankingType.ALL)
        .toResult(schedulerProvider)
        .toLiveData()
  }
}
