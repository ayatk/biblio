/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.util.helper

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.webkit.URLUtil
import com.ayatk.biblio.R
import com.ayatk.biblio.di.scope.ActivityScope
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject
constructor(val activity: AppCompatActivity) {

  fun navigateToNovelDetail(novel: Novel) {
    activity.startActivity(NovelDetailActivity.createIntent(activity, novel))
  }

  fun navigateToNovelBody(novel: Novel, page: Int) {
    activity.startActivity(NovelBodyActivity.createIntent(activity, novel, page))
  }

  fun navigateToWebPage(url: String) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
      return
    }

    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setToolbarColor(ContextCompat.getColor(activity, R.color.app_blue))
        .build()

    intent.launchUrl(activity, Uri.parse(url))
  }

  interface ConfirmDialogListener {

    fun onClickPositiveButton()

    fun onClickNegativeButton()
  }
}
