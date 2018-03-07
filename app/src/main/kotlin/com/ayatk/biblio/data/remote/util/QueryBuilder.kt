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

package com.ayatk.biblio.data.remote.util

import com.ayatk.biblio.data.entity.enums.OutputOrder
import com.ayatk.biblio.data.remote.exception.NarouOutOfRangeException
import java.net.URLEncoder

class QueryBuilder {
  // すべてjsonでResponseを要求するためout=jsonを指定
  private var query = listOf(Pair("out", "json"))

  companion object {
    private const val REQUEST_MIN_NOVEL_SIZE = 1
    private const val REQUEST_MAX_NOVEL_SIZE = 500
    private const val REQUEST_MIN_START_SIZE = 1
    private const val REQUEST_MAX_START_SIZE = 2000
  }

  /**
   * ncodeを指定して小説情報を取得します
   *
   * @param ncode
   */
  fun ncode(vararg ncode: String): QueryBuilder {
    query += Pair("ncode", ncode.joinToString("-", transform = String::toLowerCase))
    return this
  }

  /**
   * 小説の最大出力数を指定できます．
   * 指定しない場合は20件です．
   *
   * @param size int (1 ~ 500)
   * @return QueryBuilder
   */
  fun size(size: Int): QueryBuilder {
    if (size !in REQUEST_MIN_NOVEL_SIZE..REQUEST_MAX_NOVEL_SIZE) {
      throw NarouOutOfRangeException("out of output limit (1 ~ 500)")
    }

    query += Pair("lim", size.toString())
    return this
  }

  /**
   * 表示開始位置の指定です．
   * たとえば全部で10作品あるとして，3作品目以降の小説情報を取得したい場合は3と指定してください．
   *
   * @param start Int 1 ~ 2000
   * @return QueryBuilder
   */
  fun start(start: Int): QueryBuilder {
    if (start !in REQUEST_MIN_START_SIZE..REQUEST_MAX_START_SIZE) {
      throw NarouOutOfRangeException("out of start number (1 ~ 2000)")
    }

    query += Pair("st", start.toString())
    return this
  }

  /**
   * 出力順序を指定します．
   * 指定しない場合は新着順となります．
   *
   * @param order OutputOrder [OutputOrder]
   * @return QueryBuilder
   */
  fun order(order: OutputOrder): QueryBuilder {
    query += Pair("order", order.id)
    return this
  }

  /**
   * 検索単語を指定します．
   * 半角または全角スペースで区切り，複数指定するとAND検索になります．
   * 検索は部分一致です．
   *
   * @param words String
   * @return QueryBuilder
   */
  fun searchWords(vararg words: String): QueryBuilder {
    query += Pair("word", URLEncoder.encode(words.joinToString(" "), "UTF-8"))
    return this
  }

  /**
   * 除外単語を指定します．
   * 半角または全角スペースで区切り，複数指定すると単語を増やせます．
   * 除外は部分一致です．
   *
   * @param words String
   * @return QueryBuilder
   */
  fun notWords(vararg words: String): QueryBuilder {
    query += Pair("notword", URLEncoder.encode(words.joinToString(" "), "UTF-8"))
    return this
  }

  /**
   * ピックアップ条件に当てはまる小説を指定します．
   * true で最終掲載日(general_lastup)から60日以内でなおかつ「短編または完結済または10万文字以上の連載中」
   * false で上記ピックアップ条件を満たさない作品
   *
   * @param isPickup boolean
   * @return QueryBuilder
   */
  fun pickup(isPickup: Boolean): QueryBuilder {
    query += Pair("ispickup", if (isPickup) "1" else "0")
    return this
  }

  /**
   * 設定したQueryをMapに変換します
   * @return Map<String, String>
   */
  fun build(): Map<String, String> {
    return query.toMap()
  }
}
