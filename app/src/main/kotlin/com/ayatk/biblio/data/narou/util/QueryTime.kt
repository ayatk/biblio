/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.util

import com.ayatk.biblio.util.FORMAT_yyyyMMdd
import java.util.Calendar
import java.util.Date

/**
 * ランキング用の日付を生成するクラス
 *
 * @since 0.1.0
 */
object QueryTime {

  private var cal: Calendar = Calendar.getInstance()

  fun day2String(date: Date): String {
    return FORMAT_yyyyMMdd.format(cal.apply { cal.time = date }.time)
  }

  /**
   * 指定された日付の月初めの日付を計算します
   *
   * @sample 2017/2/13 -> 2017/2/1
   * @param date
   * @return String 2017/2/1 -> 20170201
   */
  fun day2MonthOne(date: Date): String {
    return FORMAT_yyyyMMdd.format(cal.apply {
      time = date
      set(Calendar.DAY_OF_MONTH, 1)
    }.time)
  }

  /**
   *
   */
  fun day2Tuesday(date: Date): String {
    return FORMAT_yyyyMMdd.format(cal.apply {
      time = date
      var week = get(Calendar.DAY_OF_WEEK)
      if (week == Calendar.MONDAY || week == Calendar.SUNDAY) {
        week += 7
      }
      add(Calendar.DAY_OF_MONTH, -week)
      add(Calendar.DAY_OF_MONTH, Calendar.TUESDAY)
    }.time)
  }
}
