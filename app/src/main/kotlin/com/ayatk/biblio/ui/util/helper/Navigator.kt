/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
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
import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.detail.NovelDetailActivity

object Navigator {

  fun navigateToNovelDetail(context: Context, novel: Novel) {
    context.startActivity(NovelDetailActivity.createIntent(context, novel))
  }

  fun navigateToNovelBody(context: Context, novel: Novel, page: Int) {
    context.startActivity(NovelBodyActivity.createIntent(context, novel, page))
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
