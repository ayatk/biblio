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

package com.ayatk.biblio.data.narou

import com.ayatk.biblio.data.entity.enums.RankingType
import com.ayatk.biblio.data.narou.entity.NarouEpisode
import com.ayatk.biblio.data.narou.entity.NarouIndex
import com.ayatk.biblio.data.narou.entity.NarouRanking
import com.ayatk.biblio.data.narou.entity.mapper.toNovel
import com.ayatk.biblio.data.narou.service.NarouApiService
import com.ayatk.biblio.data.narou.service.NarouService
import com.ayatk.biblio.data.narou.util.HtmlUtil
import com.ayatk.biblio.data.narou.util.QueryTime
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Single
import org.jsoup.Jsoup
import java.util.Date
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NarouClient @Inject constructor(
    private val htmlUtil: HtmlUtil,
    private val narouApiService: NarouApiService,
    @Named("Narou") private val narouService: NarouService,
    @Named("Narou18") private val narou18Service: NarouService
) {

  fun getNovel(query: Map<String, String>): Single<List<Novel>> {
    return narouApiService.getNovel(query)
        // 0番目にall nullの要素が入ってしまってるのでdrop(1)しないと落ちる
        .map { novels -> novels.drop(1).toNovel(Publisher.NAROU) }
  }

  fun getNovel18(query: Map<String, String>): Single<List<Novel>> {
    return narouApiService.getNovel18(query)
        // 0番目にall nullの要素が入ってしまってるのでdrop(1)しないと落ちる
        .map { novels -> novels.drop(1).toNovel(Publisher.NOCTURNE_MOONLIGHT) }
  }

  fun getRanking(date: Date, rankingType: RankingType): Single<List<NarouRanking>> {
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

  fun getTableOfContents(ncode: String): Single<List<NarouIndex>> {
    return narouService.getTableOfContents(ncode.toLowerCase())
        .map { s -> htmlUtil.parseTableOfContents(ncode, s) }
  }

  fun getAllPages(ncode: String): Single<List<NarouEpisode>> {
    return narouService.getPage(ncode.toLowerCase(), 1).map { body ->
      val page = Jsoup
          .parse(body)
          .select("#novel_no")[0]
          .text()
          .replace("^[1-9]+/".toRegex(), "")
          .toInt()

      var novelList = listOf<NarouEpisode>()
      for (i in 1..page) {
        novelList += getPage(ncode, i).blockingGet()
      }
      return@map novelList
    }
  }

  fun getPage(ncode: String, page: Int): Single<NarouEpisode> =
      narouService.getPage(ncode, page).map { s -> htmlUtil.parsePage(ncode, s, page) }

  fun getSSPage(ncode: String): Single<NarouEpisode> =
      narouService.getSSPage(ncode).map { htmlUtil.parsePage(ncode, it, 1) }
}
