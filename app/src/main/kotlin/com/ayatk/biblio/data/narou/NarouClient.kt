/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou

import com.ayatk.biblio.data.narou.entity.NarouNovel
import com.ayatk.biblio.data.narou.entity.NarouNovelBody
import com.ayatk.biblio.data.narou.entity.NarouNovelTable
import com.ayatk.biblio.data.narou.entity.NarouRanking
import com.ayatk.biblio.data.narou.entity.enums.RankingType
import com.ayatk.biblio.data.narou.service.NarouApiService
import com.ayatk.biblio.data.narou.service.NarouService
import com.ayatk.biblio.data.narou.util.HtmlUtil
import com.ayatk.biblio.data.narou.util.QueryTime
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Single
import org.jsoup.Jsoup
import java.util.Date
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NarouClient
@Inject constructor(
    private val htmlUtil: HtmlUtil,
    private val narouApiService: NarouApiService,
    @Named("Narou") private val narouService: NarouService,
    @Named("Narou18") private val narou18Service: NarouService
) {

  fun getNovel(query: Map<String, String>): Single<List<Novel>> {
    return narouApiService.getNovel(query)
        // 0番目にall nullの要素が入ってしまってるのでdrop(1)しないと落ちる
        .map { novels -> convertNarouNovelToNovel(novels.drop(1), Publisher.NAROU) }
  }

  fun getNovel18(query: Map<String, String>): Single<List<Novel>> {
    return narouApiService.getNovel18(query)
        // 0番目にall nullの要素が入ってしまってるのでdrop(1)しないと落ちる
        .map { novels -> convertNarouNovelToNovel(novels.drop(1), Publisher.NOCTURNE_MOONLIGHT) }
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

  fun getTableOfContents(ncode: String): Single<List<NarouNovelTable>> {
    return narouService.getTableOfContents(ncode.toLowerCase())
        .map { s -> htmlUtil.parseTableOfContents(ncode, s) }
  }

  fun getAllPages(ncode: String): Single<List<NarouNovelBody>> {
    return narouService.getPage(ncode.toLowerCase(), 1).map { body ->
      val page = Jsoup
          .parse(body)
          .select("#novel_no")[0]
          .text()
          .replace("^[1-9]+/".toRegex(), "")
          .toInt()

      var novelList = listOf<NarouNovelBody>()
      for (i in 1..page) {
        novelList += getPage(ncode, i).blockingGet()
      }
      return@map novelList
    }
  }

  fun getPage(ncode: String, page: Int): Single<NarouNovelBody>
      = narouService.getPage(ncode, page).map { s -> htmlUtil.parsePage(ncode, s, page) }

  fun getSSPage(ncode: String): Single<NarouNovelBody>
      = narouService.getSSPage(ncode).map { htmlUtil.parsePage(ncode, it, 1) }

  private fun convertNarouNovelToNovel(
      narouNovels: List<NarouNovel>, publisher: Publisher
  ): List<Novel> {
    return narouNovels.map {
      Novel(
          publisher = publisher,
          code = it.ncode,
          title = it.title,
          writer = it.writer,
          writerId = it.userID.toString(),
          story = it.story,
          novelTags = it.keyword.split(" "),
          firstUpdateDate = it.firstup,
          lastUpdateDate = it.lastup,
          novelState = if (it.novelType == 2) {
            NovelState.SHORT_STORY
          } else if (it.novelType == 1 && it.end == 1) {
            NovelState.SERIES
          } else {
            NovelState.SERIES_END
          },
          totalPages = it.page,
          allRateCount = it.raterCount,
          reviewCount = it.reviewCount,
          bookmarkCount = it.bookmarkCount,
          length = it.length,
          original = it.gensaku,
          isOrigin = true,
          isR15 = it.isR15 == 1,
          isR18 = publisher == Publisher.NOCTURNE_MOONLIGHT
      )
    }
  }
}
