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

package com.ayatk.biblio.ui.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityDetailBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.detail.index.IndexFragment
import com.ayatk.biblio.ui.detail.info.InfoFragment
import com.ayatk.biblio.ui.util.initBackToolbar
import com.ayatk.biblio.util.ext.extraOf
import dagger.android.support.DaggerAppCompatActivity

class DetailActivity : DaggerAppCompatActivity() {

  private val binding: ActivityDetailBinding by lazy {
    DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
  }

  private val novel: Novel by lazy {
    intent.extras.get(EXTRA_NOVEL) as Novel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding.toolbar.title = novel.title
    initBackToolbar(this, binding.toolbar)

    // ViewPager
    val viewPager = binding.containerPager
    viewPager.adapter = DetailPagerAdapter(supportFragmentManager)
    binding.tab.setupWithViewPager(viewPager)
  }

  companion object {

    private val EXTRA_NOVEL = "extra_novel"

    fun createIntent(context: Context, novel: Novel): Intent =
        Intent(context, DetailActivity::class.java).extraOf(
            EXTRA_NOVEL to novel
        )
  }

  inner class DetailPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        DetailPage.values()[position].createFragment(novel)

    override fun getCount(): Int =
        DetailPage.values().size

    override fun getPageTitle(position: Int): CharSequence =
        getString(DetailPage.values()[position].title)
  }

  private enum class DetailPage(val title: Int) {
    INDEX(R.string.novel_index_title) {
      override fun createFragment(novel: Novel): Fragment = IndexFragment.newInstance(novel)
    },
    INFO(R.string.novel_info_title) {
      override fun createFragment(novel: Novel): Fragment = InfoFragment.newInstance(novel)
    };

    abstract fun createFragment(novel: Novel): Fragment
  }
}
