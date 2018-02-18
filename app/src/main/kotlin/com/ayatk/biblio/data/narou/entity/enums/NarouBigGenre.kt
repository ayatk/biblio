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

package com.ayatk.biblio.data.narou.entity.enums

enum class NarouBigGenre constructor(val type: Int) {
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
    fun of(id: Int): NarouBigGenre = values().firstOrNull { it.type == id } ?: OTHER
  }
}
