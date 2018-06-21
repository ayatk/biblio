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
import com.ayatk.biblio.data.remote.service.NarouApiService
import com.ayatk.biblio.data.remote.service.NarouService
import com.ayatk.biblio.data.remote.util.HtmlParser
import com.ayatk.biblio.data.remote.util.QueryTime
import com.ayatk.biblio.di.scope.Narou
import io.reactivex.Flowable
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NarouDataStoreImpl @Inject constructor(
  private val htmlParser: HtmlParser,
  private val narouApiService: NarouApiService,
  @Narou private val narouService: NarouService
) : NarouDataStore {

  override fun getNovel(query: Map<String, String>): Flowable<List<NarouNovel>> =
    narouApiService.getNovel(query)
      // 0番目にall nullの要素が入ってしまってるのでdrop(1)しないと落ちる
      .map { novels -> novels.drop(1) }

  override fun getIndex(code: String): Flowable<List<NarouIndex>> =
    narouService.getTableOfContents(code.toLowerCase())
      .map { htmlParser.parseTableOfContents(code, it) }

  override fun getEpisode(code: String, page: Int): Flowable<NarouEpisode> =
    narouService.getPage(code, page)
      .map { htmlParser.parsePage(code, it, page) }

  override fun getShortStory(code: String): Flowable<NarouEpisode> =
    narouService.getSSPage(code)
      .map { htmlParser.parsePage(code, it, 1) }

  override fun getRanking(rankingType: RankingType, date: Date): Flowable<List<NarouRanking>> {
    var dateStr = ""
    when (rankingType) {
      RankingType.DAILY -> dateStr = QueryTime.day2String(date)
      RankingType.WEEKLY -> dateStr = QueryTime.day2Tuesday(date)
      RankingType.MONTHLY -> dateStr = QueryTime.day2MonthOne(date)
      RankingType.QUARTET -> dateStr = QueryTime.day2MonthOne(date)
      RankingType.ALL -> IllegalArgumentException("Not arrowed ALL type request")
    }
    return narouApiService.getRanking(dateStr + rankingType.type)
  }
}
