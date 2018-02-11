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

package com.ayatk.biblio.repository.ranking

import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Single

interface RankingRepository {

  fun getDailyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getWeeklyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getMonthlyRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getQuarterRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>

  fun getAllRank(publisher: Publisher, range: IntRange): Single<List<Ranking>>
}
