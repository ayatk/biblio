/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityWebBinding

class WebActivity : BaseActivity() {

  private val TAG = WebActivity::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding: ActivityWebBinding = DataBindingUtil.setContentView(this, R.layout.activity_web)

    val title = intent.getStringExtra(EXTRA_TITLE)
    val url = intent.getStringExtra(EXTRA_URL)

    binding.toolbar.title = title
    setSupportActionBar(binding.toolbar)

    initBackToolbar(binding.toolbar)
    // WebViewによる表示
    binding.webView.loadUrl(url)
  }

  companion object {

    private val EXTRA_TITLE = "TITLE"
    private val EXTRA_URL = "URL"

    fun createIntent(context: Context, title: String, url: String): Intent {
      return Intent(context, WebActivity::class.java).apply {
        putExtra(EXTRA_TITLE, title)
        putExtra(EXTRA_URL, url)
      }
    }
  }
}
