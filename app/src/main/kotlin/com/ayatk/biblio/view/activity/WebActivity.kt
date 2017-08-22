/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityWebBinding

class WebActivity : BaseActivity() {

  private val TAG = WebActivity::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding: ActivityWebBinding = DataBindingUtil.setContentView(this, R.layout.activity_web)

    val title = intent.getStringExtra("title")
    val url = intent.getStringExtra("url")

    binding.toolbar.title = title
    setSupportActionBar(binding.toolbar)

    initBackToolbar(binding.toolbar)
    // WebViewによる表示
    binding.webView.loadUrl(url)
  }
}
