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

package com.ayatk.biblio.model.enums

import android.support.annotation.StringRes
import com.ayatk.biblio.R

enum class Genre(@StringRes val genreName: Int) {
  /**
   * 異世界〔恋愛〕 LOVE_DIFF_WORLD
   */
  LOVE_DIFF_WORLD(R.string.genre_love_diff_world),
  /**
   * 現実世界〔恋愛〕 LOVE_REAL_WORLD
   */
  LOVE_REAL_WORLD(R.string.genre_love_real_world),
  /**
   * ハイファンタジー〔ファンタジー〕 FANTASY_HIGH
   */
  FANTASY_HIGH(R.string.genre_fantasy_high),
  /**
   * ハイファンタジー〔ファンタジー〕 FANTASY_LOW
   */
  FANTASY_LOW(R.string.genre_fantasy_low),
  /**
   * 純文学〔文学〕 LITERAL_PURE
   */
  LITERAL_PURE(R.string.genre_literal_pure),
  /**
   * ヒューマンドラマ〔文学〕 LITERAL_DRAMA
   */
  LITERAL_DRAMA(R.string.genre_literal_drama),
  /**
   * 歴史〔文学〕 LITERAL_HISTORY
   */
  LITERAL_HISTORY(R.string.genre_literal_history),
  /**
   * 推理〔文学〕 LITERAL_DETECTIVE
   */
  LITERAL_DETECTIVE(R.string.genre_literal_detective),
  /**
   * ホラー〔文学〕 LITERAL_HORROR
   */
  LITERAL_HORROR(R.string.genre_literal_horror),
  /**
   * アクション〔文学〕 LITERAL_ACTION
   */
  LITERAL_ACTION(R.string.genre_literal_action),
  /**
   * コメディ〔文学〕 LITERAL_COMEDY
   */
  LITERAL_COMEDY(R.string.genre_literal_comedy),
  /**
   * VRゲーム 〔SF〕 SF_VR
   */
  SF_VR(R.string.genre_sf_vr),
  /**
   * 宇宙 〔SF〕 SF_SPACE
   */
  SF_SPACE(R.string.genre_sf_space),
  /**
   * 空想科学 〔SF〕 SF_SCIENCE
   */
  SF_SCIENCE(R.string.genre_sf_science),
  /**
   * パニック 〔SF〕 SF_PANIC
   */
  SF_PANIC(R.string.genre_sf_panic),
  /**
   * 童話 〔その他〕 OTHER_FAIRYTALE
   */
  OTHER_FAIRYTALE(R.string.genre_other_fairytale),
  /**
   * 詩 〔その他〕 OTHER_POEM
   */
  OTHER_POEM(R.string.genre_other_poem),
  /**
   * エッセイ 〔その他〕 OTHER_ESSAY
   */
  OTHER_ESSAY(R.string.genre_other_essay),
  /**
   * リプレイ 〔その他〕 OTHER_REPLAY
   */
  OTHER_REPLAY(R.string.genre_other_replay),
  /**
   * その他 〔その他〕 OTHER
   */
  OTHER(R.string.genre_other),
  /**
   * ノンジャンル 〔ノンジャンル〕 NON_GENRE
   */
  NON_GENRE(R.string.genre_non_genre)
}
