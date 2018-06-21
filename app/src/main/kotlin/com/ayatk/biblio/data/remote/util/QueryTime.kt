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

  private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

  private var cal: Calendar = Calendar.getInstance()

  fun day2String(date: Date): String = dateFormat.format(cal.apply { cal.time = date }.time)

  /**
   * 指定された日付の月初めの日付を計算します
   *
   * @sample 2017/2/13 -> 2017/2/1
   * @param date
   * @return String 2017/2/1 -> 20170201
   */
  fun day2MonthOne(date: Date): String = dateFormat.format(
    cal.apply {
      time = date
      set(Calendar.DAY_OF_MONTH, 1)
    }.time
  )

  /**
   *
   */
  fun day2Tuesday(date: Date): String =
    dateFormat.format(
      cal.apply {
        time = date
        var week = get(Calendar.DAY_OF_WEEK)
        if (week == Calendar.MONDAY || week == Calendar.SUNDAY) {
          week += Calendar.DAY_OF_WEEK
        }
        add(Calendar.DAY_OF_MONTH, -week)
        add(Calendar.DAY_OF_MONTH, Calendar.TUESDAY)
      }.time
    )
}
