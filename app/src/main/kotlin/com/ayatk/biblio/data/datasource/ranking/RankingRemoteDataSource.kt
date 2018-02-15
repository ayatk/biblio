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

package com.ayatk.biblio.data.datasource.ranking

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.NarouRanking
import com.ayatk.biblio.data.narou.entity.enums.NarouRankingType
import com.ayatk.biblio.data.narou.entity.enums.OutputOrder
import com.ayatk.biblio.data.narou.entity.mapper.toRanking
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.domain.repository.RankingRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.model.enums.RankingType
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class RankingRemoteDataSource @Inject constructor(
    private val narouClient: NarouClient,
    private val schedulerProvider: SchedulerProvider
) : RankingRepository {

  companion object {
    private const val EARLY_MORNING = 6
  }

  override fun narouRanking(rankingType: RankingType, range: IntRange): Flowable<List<Ranking>> {
    val narouRankingType = NarouRankingType.valueOf(rankingType.name)
    val today = Calendar.getInstance()

    when (narouRankingType) {
      NarouRankingType.DAILY,
      NarouRankingType.WEEKLY -> {
        // 午前6時以前にその日のランキングを取得するとエラーで死ぬので前日のランキングを取得
        if (today.get(Calendar.HOUR_OF_DAY) < EARLY_MORNING) {
          today.add(Calendar.DATE, -1)
        }

        return narouClient.getRanking(today.time, narouRankingType)
            .flatMap {
              val codes = it
                  .map { it.ncode }
                  .drop(range.first)
                  .take(range.count())

              narouClient.getNovel(QueryBuilder().ncode(*codes.toTypedArray()).size(range.count()).build())
                  .map { novel ->
                    it.drop(range.first)
                        .take(range.count())
                        .toRanking(novel)
                  }
            }
            .toFlowable()
      }
      NarouRankingType.MONTHLY,
      NarouRankingType.QUARTET -> {
        return narouClient.getRanking(today.time, NarouRankingType.DAILY)
            .flatMap {
              val codes = it
                  .map { it.ncode }
                  .drop(range.first)
                  .take(range.count())

              narouClient.getNovel(QueryBuilder().ncode(*codes.toTypedArray()).size(range.count()).build())
                  .map { novel ->
                    it.drop(range.first)
                        .take(range.count())
                        .toRanking(novel)
                  }
            }
            .toFlowable()
      }
      NarouRankingType.ALL -> {
        val query = QueryBuilder().order(OutputOrder.HYOKA_COUNT).size(range.last).build()
        return narouClient
            .getNovel(query)
            .map { it.toRanking() }
            .toFlowable()
      }
    }
  }

  override fun nocturneRanking(rankingType: RankingType, range: IntRange): Flowable<List<Ranking>> {
    TODO("not implemented")
  }
}
