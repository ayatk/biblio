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

import com.ayatk.biblio.domain.repository.RankingRepository
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.model.enums.RankingType
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Flowable
import javax.inject.Inject

class RankingUseCaseImpl @Inject constructor(
    private val repository: RankingRepository,
    private val schedulerProvider: SchedulerProvider
) : RankingUseCase {

  companion object {
    private const val RANK_SIZE = 300
  }

  override fun ranking(publisher: Publisher, rankingType: RankingType): Flowable<List<Ranking>> {
    return when (publisher) {
      Publisher.NAROU ->
        repository.narouRanking(rankingType, 0 until RANK_SIZE)
            .subscribeOn(schedulerProvider.io())

      Publisher.NOCTURNE_MOONLIGHT ->
        repository.nocturneRanking(rankingType, 0 until RANK_SIZE)
            .subscribeOn(schedulerProvider.io())
    }
  }
}
