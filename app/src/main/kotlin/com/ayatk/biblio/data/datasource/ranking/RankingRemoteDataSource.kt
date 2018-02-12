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
import com.ayatk.biblio.data.narou.entity.enums.OutputOrder
import com.ayatk.biblio.data.narou.entity.enums.RankingType
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.domain.repository.RankingRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.util.rx.SchedulerProvider
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

  override fun getDailyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>> {
    val today = Calendar.getInstance()
    // 午前6時以前にその日のランキングを取得するとエラーで死ぬので前日のランキングを取得
    if (today.get(Calendar.HOUR_OF_DAY) < EARLY_MORNING) {
      today.add(Calendar.DATE, -1)
    }

    return narouClient.getRanking(today.time, RankingType.DAILY)
        .flatMap {
          val codes = it.map { it.ncode }.drop(range.first).take(range.count())
          narouClient.getNovel(QueryBuilder().ncode(*codes.toTypedArray()).size(range.count()).build())
              .map { novel -> convertRanking(it.drop(range.first).take(range.count()), novel) }
        }
        .subscribeOn(schedulerProvider.io())
  }

  override fun getWeeklyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>> {
    val today = Calendar.getInstance()
    // 午前6時以前にその日のランキングを取得するとエラーで死ぬので前週のランキングを取得
    if (today.get(Calendar.HOUR_OF_DAY) < EARLY_MORNING) {
      today.add(Calendar.DATE, -1)
    }

    return narouClient.getRanking(today.time, RankingType.WEEKLY)
        .flatMap {
          val codes = it.map { it.ncode }.drop(range.first).take(range.count())
          narouClient.getNovel(QueryBuilder().ncode(*codes.toTypedArray()).size(range.count()).build())
              .map { novel -> convertRanking(it.drop(range.first).take(range.count()), novel) }
        }
        .subscribeOn(schedulerProvider.io())
  }

  override fun getMonthlyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>> =
      narouClient.getRanking(Date(), RankingType.MONTHLY)
          .flatMap {
            val codes = it.map { it.ncode }.drop(range.first).take(range.count())
            narouClient.getNovel(QueryBuilder().ncode(*codes.toTypedArray()).size(range.count()).build())
                .map { novel -> convertRanking(it.drop(range.first).take(range.count()), novel) }
          }
          .subscribeOn(schedulerProvider.io())

  override fun getQuarterRank(publisher: Publisher, range: IntRange): Single<List<Ranking>> =
      narouClient.getRanking(Date(), RankingType.QUARTET)
          .flatMap {
            val codes = it.map { it.ncode }.drop(range.first).take(range.count())
            narouClient.getNovel(QueryBuilder().ncode(*codes.toTypedArray()).size(range.count()).build())
                .map { novel -> convertRanking(it.drop(range.first).take(range.count()), novel) }
          }
          .subscribeOn(schedulerProvider.io())

  override fun getAllRank(publisher: Publisher, range: IntRange): Single<List<Ranking>> {
    val query = QueryBuilder().order(OutputOrder.HYOKA_COUNT).size(range.last).build()
    return narouClient.getNovel(query).map(this::convertNovel2Ranking)
        .subscribeOn(schedulerProvider.io())
  }

  private fun convertRanking(rank: List<NarouRanking>, novels: List<Novel>): List<Ranking> =
      rank.map {
        Ranking(
            rank = it.rank,
            novel = novels.firstOrNull { novel -> novel.code == it.ncode } ?: Novel(),
            point = it.pt
        )
      }

  private fun convertNovel2Ranking(novels: List<Novel>): List<Ranking> =
      novels.mapIndexed { index, novel ->
        Ranking(rank = index + 1, novel = novel, point = novel.point)
      }
}