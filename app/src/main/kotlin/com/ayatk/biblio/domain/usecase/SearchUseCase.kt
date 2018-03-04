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

package com.ayatk.biblio.domain.usecase

import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Flowable

interface SearchUseCase {

  /**
   * 検索結果を返すメソッド
   * Mapの最初は小説情報で、二つ目のBooleanはすでにライブラリにダウンロードされているかを表す。
   *
   * @param query 検索する文字
   */
  fun search(query: String): Flowable<Map<Novel, Boolean>>

  /**
   * 小説をライブラリに保存する
   *
   * @param novel 保存する小説
   */
  fun saveNovel(novel: Novel): Completable
}
