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
