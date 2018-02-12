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

package com.ayatk.biblio.ui.util.helper

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.webkit.URLUtil
import com.ayatk.biblio.R
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.detail.DetailActivity
import com.ayatk.biblio.ui.episode.EpisodeActivity

object Navigator {

  fun navigateToDetail(context: Context, novel: Novel) {
    context.startActivity(DetailActivity.createIntent(context, novel))
  }

  fun navigateToEpisode(context: Context, novel: Novel, page: Int) {
    context.startActivity(EpisodeActivity.createIntent(context, novel, page))
  }

  fun navigateToWebPage(context: Context, url: String) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
      return
    }

    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setToolbarColor(ContextCompat.getColor(context, R.color.app_blue))
        .build()

    intent.launchUrl(context, Uri.parse(url))
  }
}
