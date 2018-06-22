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

package com.ayatk.biblio.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityMainBinding
import com.ayatk.biblio.ui.search.SearchActivity
import com.ayatk.biblio.ui.util.helper.disableShiftingMode
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

  private lateinit var adapter: HomeFragmentStatePagerAdapter

  private val binding: ActivityMainBinding by lazy {
    DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setSupportActionBar(binding.toolbar)
    initBottomNav()

    adapter = HomeFragmentStatePagerAdapter(this, supportFragmentManager)
    binding.viewPager.adapter = adapter
    binding.viewPager.offscreenPageLimit = 4
    binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {
      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      }

      override fun onPageSelected(position: Int) {
        binding.toolbar.title = adapter.getPageTitle(position).toString()
      }
    })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_search -> startActivity(SearchActivity.createIntent(this))
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onPrepareOptionsMenu(menu: Menu): Boolean {
    menu.getItem(0).isVisible = binding.bottomNav.selectedItemId != R.id.nav_settings
    return super.onPrepareOptionsMenu(menu)
  }

  private fun initBottomNav() {
    binding.bottomNav.disableShiftingMode()
    binding.bottomNav.setOnNavigationItemSelectedListener {
      val page = Page.forMenuId(it.itemId)
      binding.viewPager.setCurrentItem(page.position, false)
      toggleToolbarElevation(page.toggleToolbar)
      invalidateOptionsMenu()
      true
    }
  }

  private fun toggleToolbarElevation(enable: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val elevation = if (enable) resources.getDimension(R.dimen.elevation_4dp) else 0.toFloat()
      binding.appBar.elevation = elevation
    }
  }

  private class HomeFragmentStatePagerAdapter(
    val context: Context,
    fm: FragmentManager
  ) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = Page.values()[position].createFragment()

    override fun getCount(): Int = Page.values().size

    override fun getPageTitle(position: Int): CharSequence? =
      context.getString(Page.values()[position].titleResId)
  }
}
