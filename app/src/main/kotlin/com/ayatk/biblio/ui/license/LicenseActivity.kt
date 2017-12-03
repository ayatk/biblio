/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.license

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityWebBinding

class LicenseActivity : AppCompatActivity() {

  private val binding: ActivityWebBinding by lazy {
    DataBindingUtil.setContentView<ActivityWebBinding>(this, R.layout.activity_web)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val title = intent.getStringExtra(EXTRA_TITLE)
    val url = intent.getStringExtra(EXTRA_URL)

    binding.toolbar.title = title

    initBackToolbar(binding.toolbar)
    // WebViewによる表示
    binding.webView.loadUrl(url)
  }

  private fun initBackToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
      title = toolbar.title
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
      setDisplayShowTitleEnabled(true)
      setHomeButtonEnabled(true)
      binding.toolbar.setNavigationOnClickListener { finish() }
    }
  }

  companion object {

    private val EXTRA_TITLE = "TITLE"
    private val EXTRA_URL = "URL"

    fun createIntent(context: Context?, title: String, url: String): Intent {
      return Intent(context, LicenseActivity::class.java).apply {
        putExtra(EXTRA_TITLE, title)
        putExtra(EXTRA_URL, url)
      }
    }
  }
}
