/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityNovelDetailBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.fragment.NovelInfoFragment
import com.ayatk.biblio.ui.fragment.NovelTableFragment
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.viewmodel.NovelDetailViewModel
import org.parceler.Parcels
import javax.inject.Inject

class NovelDetailActivity : BaseActivity() {

  @Inject
  lateinit var navigator: Navigator

  private val binding: ActivityNovelDetailBinding by lazy {
    DataBindingUtil.setContentView<ActivityNovelDetailBinding>(this, R.layout.activity_novel_detail)
  }

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(intent.getParcelableExtra(EXTRA_NOVEL))
  }

  private val viewModel: NovelDetailViewModel by lazy {
    NovelDetailViewModel(novel)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    component().inject(this)
    binding.viewModel = viewModel

    initToolbar()

    // ViewPager
    val viewPager = binding.containerPager
    viewPager.adapter = NovelDetailPagerAdapter(supportFragmentManager)
    binding.tab.setupWithViewPager(viewPager)
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.destroy()
  }

  private fun initToolbar() {
    setSupportActionBar(binding.toolbar)
    supportActionBar?.run {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
      setDisplayShowTitleEnabled(false)
      setHomeButtonEnabled(true)
      binding.toolbar.setNavigationOnClickListener { finish() }
    }
  }

  companion object {

    private val EXTRA_NOVEL = "extra_novel"

    fun createIntent(context: Context, novel: Novel): Intent {
      val intent = Intent(context, NovelDetailActivity::class.java)
      intent.putExtra(EXTRA_NOVEL, Parcels.wrap(novel))
      return intent
    }
  }

  inner class NovelDetailPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
      return NovelDetailPage.values()[position].createFragment(novel)
    }

    override fun getCount(): Int {
      return NovelDetailPage.values().size
    }

    override fun getPageTitle(position: Int): CharSequence {
      return getString(NovelDetailPage.values()[position].title)
    }
  }

  private enum class NovelDetailPage(val title: Int) {
    INDEX(R.string.novel_index_title) {
      override fun createFragment(novel: Novel): Fragment = NovelTableFragment.newInstance(novel)
    },
    INFO(R.string.novel_info_title) {
      override fun createFragment(novel: Novel): Fragment = NovelInfoFragment.newInstance(novel)
    };

    abstract fun createFragment(novel: Novel): Fragment
  }
}
