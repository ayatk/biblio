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

package com.ayatk.biblio.ui.license

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityWebBinding
import com.ayatk.biblio.ui.util.initBackToolbar
import com.ayatk.biblio.util.ext.extraOf

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

  companion object {

    private const val EXTRA_TITLE = "TITLE"
    private const val EXTRA_URL = "URL"

    fun createIntent(context: Context?, title: String, url: String): Intent =
        Intent(context, LicenseActivity::class.java).extraOf(
            EXTRA_TITLE to title,
            EXTRA_URL to url
        )
  }
}
