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

enum class NarouGenre constructor(val type: Int) {
  /**
   * 101: 異世界〔恋愛〕 LOVE_DIFF_WORLD
   */
  LOVE_DIFF_WORLD(101),
  /**
   * 102: 現実世界〔恋愛〕 LOVE_REAL_WORLD
   */
  LOVE_REAL_WORLD(102),
  /**
   * 201: ハイファンタジー〔ファンタジー〕 FANTASY_HIGH
   */
  FANTASY_HIGH(201),
  /**
   * 202: ハイファンタジー〔ファンタジー〕 FANTASY_LOW
   */
  FANTASY_LOW(202),
  /**
   * 301: 純文学〔文学〕 LITERAL_PURE
   */
  LITERAL_PURE(301),
  /**
   * 302: ヒューマンドラマ〔文学〕 LITERAL_DRAMA
   */
  LITERAL_DRAMA(302),
  /**
   * 303: 歴史〔文学〕 LITERAL_HISTORY
   */
  LITERAL_HISTORY(303),
  /**
   * 304: 推理〔文学〕 LITERAL_DETECTIVE
   */
  LITERAL_DETECTIVE(304),
  /**
   * 305: ホラー〔文学〕 LITERAL_HORROR
   */
  LITERAL_HORROR(305),
  /**
   * 306: アクション〔文学〕 LITERAL_ACTION
   */
  LITERAL_ACTION(306),
  /**
   * 307: コメディ〔文学〕 LITERAL_COMEDY
   */
  LITERAL_COMEDY(307),
  /**
   * 401: VRゲーム 〔SF〕 SF_VR
   */
  SF_VR(401),
  /**
   * 402: 宇宙 〔SF〕 SF_SPACE
   */
  SF_SPACE(402),
  /**
   * 403: 空想科学 〔SF〕 SF_SCIENCE
   */
  SF_SCIENCE(403),
  /**
   * 404: パニック 〔SF〕 SF_PANIC
   */
  SF_PANIC(404),
  /**
   * 9901: 童話 〔その他〕 OTHER_FAIRYTALE
   */
  OTHER_FAIRYTALE(9901),
  /**
   * 9902: 詩 〔その他〕 OTHER_POEM
   */
  OTHER_POEM(9902),
  /**
   * 9903: エッセイ 〔その他〕 OTHER_ESSAY
   */
  OTHER_ESSAY(9903),
  /**
   * 9904: リプレイ 〔その他〕 OTHER_REPLAY
   */
  OTHER_REPLAY(9904),
  /**
   * 9999: その他 〔その他〕 OTHER
   */
  OTHER(9999),
  /**
   * 9801: ノンジャンル 〔ノンジャンル〕 NONGENRE
   */
  NONGENRE(9801);

  // ------------------------ COMPANION OBJECTS ------------------------

  companion object {
    fun of(id: Int): NarouGenre = values().firstOrNull { it.type == id } ?: NONGENRE
  }
}
