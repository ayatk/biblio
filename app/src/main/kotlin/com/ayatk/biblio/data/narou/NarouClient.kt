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
import com.ayatk.biblio.data.narou.util.QueryTime
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.util.FORMAT_yyyyMMdd_kkmm
import io.reactivex.Single
import org.jsoup.Jsoup
import java.util.Date
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NarouClient
@Inject constructor(
    private val narouApiService: NarouApiService,
    @Named("Narou") private val narouService: NarouService,
    @Named("Narou18") private val narou18Service: NarouService) {

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
      RankingType.DAILY   -> dateStr = QueryTime.day2String(date)
      RankingType.WEEKLY  -> dateStr = QueryTime.day2Tuesday(date)
      RankingType.MONTHLY -> dateStr = QueryTime.day2MonthOne(date)
      RankingType.QUARTET -> dateStr = QueryTime.day2MonthOne(date)
      RankingType.ALL     -> IllegalArgumentException("Not arrowed ALL type request")
    }
    return narouApiService.getRanking(dateStr + rankingType.type)
  }

  fun getTableOfContents(ncode: String): Single<List<NarouNovelTable>> {
    return narouService.getTableOfContents(ncode.toLowerCase())
        .map { s -> parseTableOfContents(ncode, s) }
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
      = narouService.getPage(ncode, page).map { s -> parsePage(ncode, s, page) }

  fun getSSPage(ncode: String): Single<NarouNovelBody>
      = narouService.getSSPage(ncode).map { parsePage(ncode, it, 1) }

  private fun parseTableOfContents(ncode: String, body: String): List<NarouNovelTable> {

    val novelTableList = arrayListOf<NarouNovelTable>()

    val parseTable = Jsoup.parse(body)

    // 短編小説のときは目次がないのでタイトルのNarouNovelTableを生成
    if (parseTable.select(".index_box").isEmpty()) {
      val title = parseTable.select(".novel_title").text()
      val update = FORMAT_yyyyMMdd_kkmm.parse(parseTable.select("meta[name=WWWC]").attr("content"))
      return listOf(NarouNovelTable(ncode, title, false, 1, update, update))
    }

    for (element in parseTable.select(".index_box").first().children()) {
      if (element.className() == "chapter_title") {
        novelTableList.add(
            NarouNovelTable(ncode, element.text(), true, null, null, null))
      }

      if (element.className() == "novel_sublist2") {
        val el = element
            .select(".subtitle a")
            .first()

        val attrs = el
            .attr("href")
            .split("/".toRegex())
            .dropLastWhile(String::isEmpty).toTypedArray()

        val date = FORMAT_yyyyMMdd_kkmm
            .parse(element.select(".long_update")
                .text()
                .replace(" （改）", ""))

        var lastUpdate = date
        if (element.select(".long_update span").isNotEmpty()) {
          lastUpdate = FORMAT_yyyyMMdd_kkmm.parse(
              element.select(".long_update span")
                  .attr("title")
                  .replace(" 改稿", ""))
        }
        novelTableList.add(
            NarouNovelTable(ncode, el.text(), false, Integer.parseInt(attrs[2]), date, lastUpdate)
        )
      }
    }
    return novelTableList
  }

  private fun parsePage(ncode: String, body: String, page: Int): NarouNovelBody {
    val doc = Jsoup.parse(body)
    return NarouNovelBody(
        ncode = ncode,
        page = page,
        subtitle = if (doc.select(".novel_subtitle").isEmpty()) {
          doc.select(".novel_title").text()
        } else {
          doc.select(".novel_subtitle").text()
        },
        prevContent = if (doc.select("#novel_p").isNotEmpty()) doc.select(
            "#novel_p")[0].text() else "",
        content = doc.select("#novel_honbun").html()
            .replace("</?(ru?by?|rt|rp|br)>".toRegex(), ""),
        afterContent = if (doc.select("#novel_a").isNotEmpty()) doc.select(
            "#novel_a").text() else ""
    )
  }

  private fun convertNarouNovelToNovel(
      narouNovels: List<NarouNovel>, publisher: Publisher): List<Novel> {
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
