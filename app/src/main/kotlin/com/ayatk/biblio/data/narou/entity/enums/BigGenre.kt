/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.entity.enums

enum class BigGenre constructor(val type: Int) {
  /**
   * 1: 恋愛 LOVE
   */
  LOVE(1),
  /**
   * 2: ファンタジー FANTASY
   */
  FANTASY(2),
  /**
   * 3: 文学 LITERATURE
   */
  LITERATURE(3),
  /**
   * 4: SF SF
   */
  SF(4),
  /**
   * 98: ノンジャンル NONGENRE
   */
  NONGENRE(98),
  /**
   * 99: その他 OTHER
   */
  OTHER(99);

  // ------------------------ COMPANION OBJECTS ------------------------

  companion object {
    fun of(id: Int): BigGenre = values().firstOrNull { it.type == id } ?: OTHER
  }
}
