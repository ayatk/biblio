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
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityNovelBodyBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.view.fragment.NovelBodyFragment
import com.ayatk.biblio.view.helper.Navigator
import org.parceler.Parcels
import javax.inject.Inject

class NovelBodyActivity : BaseActivity() {

  @Inject
  lateinit var navigator: Navigator

  lateinit var binding: ActivityNovelBodyBinding

  lateinit private var novel: Novel

  private var page: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_novel_body)
    setSupportActionBar(binding.toolbar)
    initBackToolbar(binding.toolbar)
    component().inject(this)

    novel = Parcels.unwrap(intent.getParcelableExtra("NOVEL"))
    page = intent.getIntExtra("PAGE", 0)

    // ViewPager
    val viewPager = binding.novelViewPager
    viewPager.adapter = NovelBodyPagerAdapter(supportFragmentManager)
    viewPager.currentItem = page - 1
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        upToParentActivity()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun upToParentActivity() {
    navigator.navigateToNovelDetail(novel)
  }

  companion object {
    private val EXTRA_NOVEL = "NOVEL"

    private val EXTRA_PAGE = "PAGE"

    fun createIntent(context: Context, novel: Novel, page: Int): Intent {
      val intent = Intent(context, NovelBodyActivity::class.java)
      intent.putExtra(EXTRA_NOVEL, Parcels.wrap(novel))
      intent.putExtra(EXTRA_PAGE, page)
      return intent
    }
  }

  inner class NovelBodyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
      return NovelBodyFragment.newInstance(novel, position + 1)
    }

    override fun getCount(): Int {
      return novel.totalPages
    }
  }
}
