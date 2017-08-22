/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.model.enums

import android.support.annotation.StringRes
import com.ayatk.biblio.R

/**
 *
 */
enum class NovelState(@StringRes val stateName: Int) {
  SHORT_STORY(R.string.short_story),
  SERIES(R.string.series),
  SERIES_END(R.string.series_end);
}
