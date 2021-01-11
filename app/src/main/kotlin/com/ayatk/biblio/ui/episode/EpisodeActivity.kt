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

package com.ayatk.biblio.ui.episode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityEpisodeBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.UiEvent
import com.ayatk.biblio.ui.util.initBackToolbar
import com.ayatk.biblio.util.ext.extraOf
import dagger.android.support.DaggerAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class EpisodeActivity : DaggerAppCompatActivity() {

  private val binding: ActivityEpisodeBinding by lazy {
    DataBindingUtil.setContentView(this, R.layout.activity_episode)
  }

  private val novel: Novel by lazy {
    intent.getSerializableExtra(EXTRA_NOVEL) as Novel
  }

  private val page: Int by lazy {
    intent.getIntExtra(EXTRA_PAGE, 0)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initBackToolbar(binding.toolbar)

    // ViewPager
    binding.novelViewPager.apply {
      adapter = EpisodePagerAdapter(supportFragmentManager)
      currentItem = page
      addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
          EventBus.getDefault().post(UiEvent.EpisodeSelectedEvent(position))
        }

        override fun onPageScrolled(
          position: Int,
          positionOffset: Float,
          positionOffsetPixels: Int
        ) {
        }

        override fun onPageScrollStateChanged(pos: Int) {}
      })
    }
  }

  override fun onPause() {
    super.onPause()
    EventBus.getDefault().unregister(this)
  }

  override fun onResume() {
    super.onResume()
    EventBus.getDefault().register(this)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  @Subscribe
  fun onEvent(event: UiEvent.SubtitleChangeEvent) {
    binding.toolbar.title = event.subtitle
  }

  companion object {
    private const val EXTRA_NOVEL = "NOVEL"
    private const val EXTRA_PAGE = "PAGE"

    fun createIntent(context: Context, novel: Novel, page: Int): Intent =
      Intent(context, EpisodeActivity::class.java).extraOf(
        EXTRA_NOVEL to novel,
        EXTRA_PAGE to page
      )
  }

  inner class EpisodePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = EpisodeFragment.newInstance(novel, position)

    override fun getCount(): Int = novel.page
  }
}
