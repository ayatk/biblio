/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * ランキング用の日付を生成するクラス
 *
 * @since 0.1.0
 */
object QueryTime {

  private val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

  private var cal: Calendar = Calendar.getInstance()

  fun day2String(date: Date): String {
    cal.time = date
    return format.format(cal.time)
  }

  /**
   * 指定された日付の月初めの日付を計算します
   *
   * @sample 2017/2/13 -> 2017/2/1
   * @param date
   * @return String 2017/2/1 -> 20170201
   */
  fun day2MonthOne(date: Date): String {
    cal.time = date
    cal.set(Calendar.DAY_OF_MONTH, 1)
    return format.format(cal.time)
  }

  /**
   *
   */
  fun day2Tuesday(date: Date): String {
    cal.time = date
    var week = cal.get(Calendar.DAY_OF_WEEK)
    if (week == Calendar.MONDAY || week == Calendar.SUNDAY) {
      week += 7
    }
    cal.add(Calendar.DAY_OF_MONTH, -week)
    cal.add(Calendar.DAY_OF_MONTH, Calendar.TUESDAY)
    return format.format(cal.time)
  }
}
