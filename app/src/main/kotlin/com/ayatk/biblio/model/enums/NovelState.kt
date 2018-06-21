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

enum class NovelState(@StringRes val stateName: Int) {

  /**
   * 短編
   */
  SHORT_STORY(R.string.short_story),

  /**
   * 連載
   */
  SERIES(R.string.series),

  /**
   * 完結
   */
  SERIES_END(R.string.series_end),

  /**
   * 連載停止
   */
  SERIES_STOP(R.string.series_stop)
}
