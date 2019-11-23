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

enum class RankingType(val title: String) {

  /**
   * DAILY 日間ランキング
   */
  DAILY("日間"),
  /**
   * WEEKLY 週間ランキング
   */
  WEEKLY("週間"),
  /**
   * MONTHLY 月間ランキング
   */
  MONTHLY("月間"),
  /**
   * QUARTET 四半期ランキング
   */
  QUARTET("四半期"),
  /**
   * ALL 累計ランキング
   */
  ALL("累計")
}
