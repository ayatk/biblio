/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.util

import com.ayatk.biblio.data.narou.entity.enums.OutputOrder
import com.ayatk.biblio.data.narou.exception.NarouOutOfRangeException
import java.net.URLEncoder

class QueryBuilder {
  // すべてjsonでResponseを要求するためout=jsonを指定
  private var query = listOf(Pair("out", "json"))

  /**
   * ncodeを指定して小説情報を取得します
   *
   * @param ncode
   */
  fun ncode(vararg ncode: String): QueryBuilder {
    query += Pair("ncode", ncode.map(String::toLowerCase).joinToString("-"))
    return this
  }

  /**
   * 小説の最大出力数を指定できます．
   * 指定しない場合は20件です．
   *
   * @param lim int (1 ~ 500)
   * @return QueryBuilder
   */
  fun limit(lim: Int): QueryBuilder {
    if (lim !in 1..500) {
      throw NarouOutOfRangeException("out of output limit (1 ~ 500)")
    }

    query += Pair("lim", lim.toString())
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
    if (start !in 1..2000) {
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
