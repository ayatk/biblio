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

import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.model.enums.RankingType
import io.reactivex.Flowable

interface TopRankingUseCase {
  /**
   * ホームのランキング画面で使うランキングを取得する
   *
   * @param publisher ランキングを取ってくるサイト
   * @param rankingType 日間や月間などのランキングタイプ
   *
   * @return 指定したランキング5件分
   */
  fun ranking(publisher: Publisher, rankingType: RankingType): Flowable<List<Ranking>>
}
