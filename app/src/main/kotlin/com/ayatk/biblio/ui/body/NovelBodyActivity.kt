/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.body

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityNovelBodyBinding
import com.ayatk.biblio.event.NovelBodySelectedEvent
import com.ayatk.biblio.event.SubtitleChangeEvent
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.util.helper.Navigator
import dagger.android.support.DaggerAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.parceler.Parcels
import javax.inject.Inject

class NovelBodyActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var navigator: Navigator

  val binding: ActivityNovelBodyBinding by lazy {
    DataBindingUtil.setContentView<ActivityNovelBodyBinding>(this, R.layout.activity_novel_body)
  }

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(intent.getParcelableExtra(EXTRA_NOVEL))
  }

  private val page: Int by lazy {
    intent.getIntExtra(EXTRA_PAGE, 0)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initBackToolbar(binding.toolbar)

    EventBus.getDefault().post(NovelBodySelectedEvent(page))

    // ViewPager
    binding.novelViewPager.apply {
      adapter = NovelBodyPagerAdapter(supportFragmentManager)
      currentItem = page - 1
      addOnPageChangeListener(
          object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
              EventBus.getDefault().post(NovelBodySelectedEvent(position))
            }

            override fun onPageScrolled(position: Int, positionOffset: Float,
                positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {}
          }
      )
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

  private fun initBackToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
      title = toolbar.title
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
      setDisplayShowTitleEnabled(true)
      setHomeButtonEnabled(true)
    }
  }

  @Subscribe
  fun onEvent(event: SubtitleChangeEvent) {
    binding.toolbar.title = event.subtitle
  }

  companion object {
    private val EXTRA_NOVEL = "NOVEL"
    private val EXTRA_PAGE = "PAGE"

    fun createIntent(context: Context, novel: Novel, page: Int): Intent {
      return Intent(context, NovelBodyActivity::class.java).apply {
        putExtra(EXTRA_NOVEL, Parcels.wrap(novel))
        putExtra(EXTRA_PAGE, page)
      }
    }
  }

  inner class NovelBodyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment
        = NovelBodyFragment.newInstance(novel, position + 1)

    override fun getCount(): Int
        = novel.totalPages
  }
}
