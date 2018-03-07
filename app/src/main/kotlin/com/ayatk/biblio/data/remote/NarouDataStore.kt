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

package com.ayatk.biblio.data.remote

import com.ayatk.biblio.data.entity.enums.RankingType
import com.ayatk.biblio.data.remote.entity.NarouEpisode
import com.ayatk.biblio.data.remote.entity.NarouIndex
import com.ayatk.biblio.data.remote.entity.NarouNovel
import com.ayatk.biblio.data.remote.entity.NarouRanking
import io.reactivex.Flowable
import java.util.Date

interface NarouDataStore {

  fun getNovel(query: Map<String, String>): Flowable<List<NarouNovel>>

  fun getIndex(code: String): Flowable<List<NarouIndex>>

  fun getEpisode(code: String, page: Int): Flowable<NarouEpisode>

  fun getShortStory(code: String): Flowable<NarouEpisode>

  fun getRanking(rankingType: RankingType, date: Date): Flowable<List<NarouRanking>>
}
