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

package com.ayatk.biblio.data.narou.util

import com.ayatk.biblio.data.narou.entity.NarouEpisode
import com.ayatk.biblio.data.narou.entity.NarouIndex
import com.ayatk.biblio.util.DateFormat
import org.jsoup.Jsoup
import javax.inject.Singleton

@Singleton
class HtmlUtil {

  fun parseTableOfContents(ncode: String, body: String): List<NarouIndex> {

    val indexList = arrayListOf<NarouIndex>()

    val parseTable = Jsoup.parse(body)

    // 短編小説のときは目次がないのでタイトルのNarouIndexを生成
    if (parseTable.select(".index_box").isEmpty()) {
      val title = parseTable.select(".novel_title").text()
      val update = DateFormat.yyyyMMddkkmm
          .parse(
              parseTable.select("meta[name=WWWC]").attr("content")
          )
      return listOf(NarouIndex(0, ncode, title, false, 1, update, update))
    }

    for ((index, element) in parseTable.select(".index_box").first().children().withIndex()) {
      if (element.className() == "chapter_title") {
        indexList.add(
            NarouIndex(index, ncode, element.text(), true, null, null, null)
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
        indexList.add(
            NarouIndex(
                index, ncode, el.text(), false, Integer.parseInt(attrs[2]), date,
                lastUpdate
            )
        )
      }
    }
    return indexList
  }

  fun parsePage(ncode: String, body: String, page: Int): NarouEpisode {
    val doc = Jsoup.parse(body)
    return NarouEpisode(
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
