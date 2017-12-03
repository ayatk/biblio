/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.util.helper

import android.app.Application
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.webkit.URLUtil
import com.ayatk.biblio.R
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import javax.inject.Inject

class Navigator @Inject constructor(private val application: Application) {

  fun navigateToNovelDetail(novel: Novel) {
    application.startActivity(NovelDetailActivity.createIntent(application, novel))
  }

  fun navigateToNovelBody(novel: Novel, page: Int) {
    application.startActivity(NovelBodyActivity.createIntent(application, novel, page))
  }

  fun navigateToWebPage(url: String) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
      return
    }

    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setToolbarColor(ContextCompat.getColor(application, R.color.app_blue))
        .build()

    intent.launchUrl(application, Uri.parse(url))
  }
}
