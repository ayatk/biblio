/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.entity.enums

/**
 * ランキングの項目
 */
enum class RankingType constructor(val type: String) {
  /**
   * DAILY 日間ランキング
   */
  DAILY("-d"),
  /**
   * WEEKLY 週間ランキング
   */
  WEEKLY("-w"),
  /**
   * MONTHLY 月間ランキング
   */
  MONTHLY("-m"),
  /**
   * QUARTET 四半期ランキング
   */
  QUARTET("-q"),
  /**
   * ALL 累計ランキング
   */
  ALL("");
}
