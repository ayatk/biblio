/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.util

import com.ayatk.biblio.data.narou.entity.NarouNovelBody
import com.ayatk.biblio.data.narou.entity.NarouNovelTable
import com.ayatk.biblio.util.DateFormat
import org.jsoup.Jsoup
import javax.inject.Singleton

@Singleton
class HtmlUtil {

  fun parseTableOfContents(ncode: String, body: String): List<NarouNovelTable> {

    val novelTableList = arrayListOf<NarouNovelTable>()

    val parseTable = Jsoup.parse(body)

    // 短編小説のときは目次がないのでタイトルのNarouNovelTableを生成
    if (parseTable.select(".index_box").isEmpty()) {
      val title = parseTable.select(".novel_title").text()
      val update = DateFormat.yyyyMMddkkmm
          .parse(
              parseTable.select("meta[name=WWWC]").attr("content")
          )
      return listOf(NarouNovelTable(0, ncode, title, false, 1, update, update))
    }

    for ((index, element) in parseTable.select(".index_box").first().children().withIndex()) {
      if (element.className() == "chapter_title") {
        novelTableList.add(
            NarouNovelTable(index, ncode, element.text(), true, null, null, null)
        )
      }

      if (element.className() == "novel_sublist2") {
        val el = element
            .select(".subtitle a")
            .first()

        val attrs = el
            .attr("href")
            .split("/".toRegex())
            .dropLastWhile(String::isEmpty).toTypedArray()

        val date = DateFormat.yyyyMMddkkmm
            .parse(
                element.select(".long_update")
                    .text()
                    .replace(" （改）", "")
            )

        var lastUpdate = date
        if (element.select(".long_update span").isNotEmpty()) {
          lastUpdate = DateFormat.yyyyMMddkkmm.parse(
              element.select(".long_update span")
                  .attr("title")
                  .replace(" 改稿", "")
          )
        }
        novelTableList.add(
            NarouNovelTable(
                index, ncode, el.text(), false, Integer.parseInt(attrs[2]), date,
                lastUpdate
            )
        )
      }
    }
    return novelTableList
  }

  fun parsePage(ncode: String, body: String, page: Int): NarouNovelBody {
    val doc = Jsoup.parse(body)
    return NarouNovelBody(
        ncode = ncode,
        page = page,
        subtitle = doc.select(
            if (doc.select(
                ".novel_subtitle"
            ).isEmpty()) ".novel_title" else ".novel_subtitle"
        ).text(),
        prevContent = getFormattedContent(
            if (doc.select("#novel_p").isNotEmpty()) doc.select(
                "#novel_p"
            )[0].text() else ""
        ),
        content = getFormattedContent(doc.select("#novel_honbun").html()),
        afterContent = getFormattedContent(
            if (doc.select("#novel_a").isNotEmpty()) doc.select(
                "#novel_a"
            ).text() else ""
        )
    )
  }

  fun getFormattedContent(content: String): String {
    return content
        .replace("\n", "")
        .replace("<br */?>".toRegex(), "\n")
        .trim { it <= '　' }
        .replace("</?(ru?by?|rt|rp)>".toRegex(), "")
  }
}
