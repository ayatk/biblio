/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityMainBinding
import com.ayatk.biblio.view.Page
import com.ayatk.biblio.view.helper.BottomNavigationViewHelper

class MainActivity : BaseActivity() {

  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    component().inject(this)

    BottomNavigationViewHelper.disableShiftingMode(binding.bottomNav)

    // init fragment
    if (savedInstanceState == null) {
      changePage(Page.forMenuId(R.id.nav_library))
    }
    setSupportActionBar(binding.toolbar)

    binding.bottomNav.setOnNavigationItemSelectedListener({
      changePage(Page.forMenuId(it.itemId))
      true
    })
  }

  private fun toggleToolbarElevation(enable: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val elevation = if (enable) resources.getDimension(R.dimen.elevation_4dp) else 0.toFloat()
      binding.appBar.elevation = elevation
    }
  }

  private fun changePage(page: Page) {
    binding.toolbar.setTitle(page.titleResId)
    toggleToolbarElevation(page.toggleToolbar)
    replaceFragment(page.createFragment(), R.id.container)
  }

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }
}
