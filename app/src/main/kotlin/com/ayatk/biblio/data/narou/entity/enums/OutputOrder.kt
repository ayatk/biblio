/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.entity.enums

/**
 * 小説の出力順序
 */
enum class OutputOrder constructor(val id: String) {
  /**
   * BOOKMARK_COUNT ブックマーク数の多い順
   */
  BOOKMARK_COUNT("favnovelcnt"),
  /**
   * REVIEW_COUNT レビュー数の多い順
   */
  REVIEW_COUNT("reviewcnt"),
  /**
   * TOTAL_POINT 総合評価の高い順
   */
  TOTAL_POINT("hyoka"),
  /**
   * TOTAL_POINT_ASC 総合評価の低い順
   */
  TOTAL_POINT_ASC("hyokaasc"),
  /**
   * IMPRESSION_COUNT 感想の多い順
   */
  IMPRESSION_COUNT("impressioncnt"),
  /**
   * HYOKA_COUNT 評価者数の多い順
   */
  HYOKA_COUNT("hyokacnt"),
  /**
   * HYOKA_COUNT_ASC 評価者数の少ない順
   */
  HYOKA_COUNT_ASC("hyokacntasc"),
  /**
   * WEEKLY_UNIQUE_USER 週間ユニークユーザの多い順
   */
  WEEKLY_UNIQUE_USER("weekly"),
  /**
   * CHARACTER_LENGTH_DESC 小説本文の文字数が多い順
   */
  CHARACTER_LENGTH_DESC("lengthdesc"),
  /**
   * CHARACTER_LENGTH_ASC 小説本文の文字数が少ない順
   */
  CHARACTER_LENGTH_ASC("lengthasc"),
  /**
   * NCODE_DESC Nコードが新しい順
   */
  NCODE_DESC("ncodedesc"),
  /**
   * OLD 古い順
   */
  OLD("old");
}
