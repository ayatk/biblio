/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.util

import com.ayatk.biblio.data.narou.entity.enums.OutputOrder
import com.ayatk.biblio.data.narou.exception.NarouOutOfRangeException
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class QueryBuilderTest {

  @Test
  fun setNcodeQueryTest() {
    val ans = mapOf(Pair("out", "json"), Pair("ncode", "n1234ab"))

    assertThat(ans, `is`(QueryBuilder().ncode("n1234ab").build()))
  }

  @Test
  fun setNcodeArrayQueryTest() {
    val ncodeArray = arrayOf("n1234ab", "n2345bc", "n3456cd")
    val ans = mapOf(Pair("out", "json"), Pair("ncode", ncodeArray.joinToString("-")))

    assertThat(ans, `is`(QueryBuilder().ncode(*ncodeArray).build()))
  }

  @Test
  fun setLimitQueryTest() {
    val ans = mapOf(Pair("out", "json"), Pair("lim", "300"))

    assertThat(ans, `is`(QueryBuilder().size(300).build()))
  }

  @Test
  fun throwLimitQueryTest() {

    val throwNums = arrayOf(
        0, // zero
        -1, // negative value
        501  // over value
    )

    throwNums.forEach {
      try {
        QueryBuilder().size(it).build()
      } catch (e: NarouOutOfRangeException) {
        assertThat("out of output limit (1 ~ 500)", `is`(e.message))
      }
    }
  }

  @Test
  fun setStartQueryTest() {
    val ans = mapOf(Pair("out", "json"), Pair("st", "300"))

    assertThat(ans, `is`(QueryBuilder().start(300).build()))
  }

  @Test
  fun throwStartQueryTest() {

    val throwNums = arrayOf(
        0, // zero
        -1, // negative value
        2001  // over value
    )

    throwNums.forEach {
      try {
        QueryBuilder().start(it).build()
      } catch (e: NarouOutOfRangeException) {
        assertThat("out of start number (1 ~ 2000)", `is`(e.message))
      }
    }
  }

  @Test
  fun setOrderQueryTest() {
    val ans = mapOf(Pair("out", "json"))

    OutputOrder.values().forEach {
      assertThat(ans.plus(Pair("order", it.id)), `is`(QueryBuilder().order(it).build()))
    }
  }

  @Test
  fun setSearchWordsTest() {
    // ほげ
    val ans = mapOf(Pair("out", "json"), Pair("word", "%E3%81%BB%E3%81%92"))
    assertThat(ans, `is`(QueryBuilder().searchWords("ほげ").build()))
  }

  @Test
  fun setSearchWordsArrayQueryTest() {
    val wordsArray = arrayOf("ほげ", "ふが", "ぴよ")
    // ほげ+ふが+ぴよ
    val ans = mapOf(Pair("out", "json"),
        Pair("word", "%E3%81%BB%E3%81%92+%E3%81%B5%E3%81%8C+%E3%81%B4%E3%82%88"))

    assertThat(ans, `is`(QueryBuilder().searchWords(*wordsArray).build()))
  }

  @Test
  fun setNotSearchWordsTest() {
    // ほげ
    val ans = mapOf(Pair("out", "json"), Pair("notword", "%E3%81%BB%E3%81%92"))
    assertThat(ans, `is`(QueryBuilder().notWords("ほげ").build()))
  }

  @Test
  fun setNotSearchWordsArrayQueryTest() {
    val wordsArray = arrayOf("ほげ", "ふが", "ぴよ")
    // ほげ+ふが+ぴよ
    val ans = mapOf(Pair("out", "json"),
        Pair("notword", "%E3%81%BB%E3%81%92+%E3%81%B5%E3%81%8C+%E3%81%B4%E3%82%88"))

    assertThat(ans, `is`(QueryBuilder().notWords(*wordsArray).build()))
  }

  @Test
  fun setPickupTest() {
    val ans = mapOf(Pair("out", "json"))

    assertThat(ans.plus(Pair("ispickup", "1")), `is`(QueryBuilder().pickup(true).build()))
    assertThat(ans.plus(Pair("ispickup", "0")), `is`(QueryBuilder().pickup(false).build()))

  }
}
