/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.ranking

import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Single

interface RankingDataSource {

  fun getDailyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getWeeklyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getMonthlyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getQuarterRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>
}
