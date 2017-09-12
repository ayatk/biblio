/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.activity

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
import com.ayatk.biblio.view.fragment.NovelInfoFragment
import com.ayatk.biblio.view.fragment.NovelTableFragment
import com.ayatk.biblio.view.helper.Navigator
import com.ayatk.biblio.viewmodel.NovelDetailViewModel
import org.parceler.Parcels
import javax.inject.Inject

class NovelDetailActivity : BaseActivity() {

  @Inject
  lateinit var navigator: Navigator

  private val binding: ActivityNovelDetailBinding by lazy {
    DataBindingUtil.setContentView<ActivityNovelDetailBinding>(this, R.layout.activity_novel_detail)
  }

  private val novel: Novel  by lazy {
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

    private val pages = listOf(
        Pair("目次", NovelTableFragment.newInstance(novel)),
        Pair("小説情報", NovelInfoFragment.newInstance(novel))
    )

    override fun getItem(position: Int): Fragment? {
      return pages[position].second
    }

    override fun getCount(): Int {
      return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence {
      return pages[position].first
    }
  }
}
