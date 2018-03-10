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

package com.ayatk.biblio.ui.ranking

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityRankingBinding
import com.ayatk.biblio.model.enums.RankingType
import com.ayatk.biblio.ui.util.initBackToolbar
import com.ayatk.biblio.util.ext.extraOf
import com.ayatk.biblio.util.ext.integer
import dagger.android.support.DaggerAppCompatActivity

class RankingActivity : DaggerAppCompatActivity() {

  private val binding: ActivityRankingBinding by lazy {
    DataBindingUtil.setContentView<ActivityRankingBinding>(this, R.layout.activity_ranking)
  }

  private val rankingType: RankingType by lazy {
    intent?.getSerializableExtra(EXTRA_RANKING_TYPE)!! as RankingType
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initBackToolbar(binding.toolbar)

    binding.rankingViewPager.apply {
      adapter = RankingPagerAdapter(supportFragmentManager)
      pageMargin = context.integer(R.integer.pager_margin)
      currentItem = rankingType.ordinal
    }

    binding.tab.setupWithViewPager(binding.rankingViewPager)
  }

  companion object {
    private const val EXTRA_RANKING_TYPE = "ranking_type"

    fun createIntent(context: Context, rankingType: RankingType): Intent =
        Intent(context, RankingActivity::class.java).extraOf(
            EXTRA_RANKING_TYPE to rankingType
        )
  }

  inner class RankingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        RankingListFragment.newInstance(RankingType.values()[position])

    override fun getCount(): Int = RankingType.values().size

    override fun getPageTitle(position: Int): CharSequence? =
        getString(RankingType.values()[position].title)
  }
}
