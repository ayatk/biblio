/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.util.Calendar

class QueryTimeTest {

  private var calendar = Calendar.getInstance()

  private val queryTime = QueryTime

  @Test
  fun day2MonthOneTest() {

    // It's Valentine day!!!
    calendar.set(2017, 1, 14)
    assertThat("20170201", `is`(queryTime.day2MonthOne(calendar.time)))

    // This test is Fail. But...So April Fool
    calendar.set(2017, 3, 1)
    assertThat("20170401", `is`(queryTime.day2MonthOne(calendar.time)))

    // New Year's Eve
    calendar.set(2017, 11, 31)
    assertThat("20171201", `is`(queryTime.day2MonthOne(calendar.time)))
  }

  @Test
  fun day2TuesdayTest() {

    // Monday
    calendar.set(2017, 1, 13)
    assertThat("20170207", `is`(queryTime.day2Tuesday(calendar.time)))

    // It's Valentine day!!!
    calendar.set(2017, 1, 14)
    assertThat("20170214", `is`(queryTime.day2Tuesday(calendar.time)))

    // Wednesday
    calendar.set(2017, 1, 15)
    assertThat("20170214", `is`(queryTime.day2Tuesday(calendar.time)))

    // Thursday
    calendar.set(2017, 1, 16)
    assertThat("20170214", `is`(queryTime.day2Tuesday(calendar.time)))

    // Friday
    calendar.set(2017, 1, 17)
    assertThat("20170214", `is`(queryTime.day2Tuesday(calendar.time)))

    // Saturday
    calendar.set(2017, 1, 18)
    assertThat("20170214", `is`(queryTime.day2Tuesday(calendar.time)))

    // Sunday
    calendar.set(2017, 1, 19)
    assertThat("20170214", `is`(queryTime.day2Tuesday(calendar.time)))
  }
}
