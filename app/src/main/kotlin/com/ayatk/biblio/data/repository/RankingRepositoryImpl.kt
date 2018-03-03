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

import com.ayatk.biblio.data.entity.enums.OutputOrder
import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.mapper.toRanking
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.RankingType
import io.reactivex.Flowable
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton
import com.ayatk.biblio.data.entity.enums.RankingType as ApiRankingType

@Singleton
class RankingRepositoryImpl @Inject constructor(
    private val narouClient: NarouClient
) : RankingRepository {

  companion object {
    private const val EARLY_MORNING = 6
  }

  override fun narouRanking(rankingType: RankingType, range: IntRange): Flowable<List<Ranking>> {
    val apiRankingType = ApiRankingType.valueOf(rankingType.name)
    val today = Calendar.getInstance()

    if (apiRankingType != ApiRankingType.ALL) {
      // 午前6時以前にその日のランキングを取得するとエラーで死ぬので前日のランキングを取得
      if (today.get(Calendar.HOUR_OF_DAY) < EARLY_MORNING) {
        today.add(Calendar.DATE, -1)
      }

      return narouClient.getRanking(today.time, apiRankingType)
          .flatMap {
            val codes = it
                .map { it.ncode }
                .drop(range.first)
                .take(range.count())

            val query = QueryBuilder()
                .ncode(*codes.toTypedArray())
                .size(range.count())
                .build()

            narouClient.getNovel(query)
                .map { novel ->
                  it.drop(range.first)
                      .take(range.count())
                      .toRanking(novel)
                }
          }
          .toFlowable()
    } else {
      val query = QueryBuilder().order(OutputOrder.HYOKA_COUNT).size(range.last).build()
      return narouClient
          .getNovel(query)
          .map { it.toRanking() }
          .toFlowable()
    }
  }

  override fun nocturneRanking(rankingType: RankingType, range: IntRange): Flowable<List<Ranking>> {
    TODO("not implemented")
  }
}
