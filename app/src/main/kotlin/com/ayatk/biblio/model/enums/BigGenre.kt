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

import androidx.annotation.StringRes
import com.ayatk.biblio.R

enum class BigGenre(@StringRes val genreName: Int) {
  /**
   * 恋愛 LOVE
   */
  LOVE(R.string.big_genre_love),

  /**
   * ファンタジー FANTASY
   */
  FANTASY(R.string.big_genre_fantasy),

  /**
   * 文学 LITERATURE
   */
  LITERATURE(R.string.big_genre_literal),

  /**
   * SF SF
   */
  SF(R.string.big_genre_sf),

  /**
   * ノンジャンル NON_GENRE
   */
  NON_GENRE(R.string.big_genre_non_genre),
  /**
   * その他 OTHER
   */
  OTHER(R.string.big_genre_other)
}
