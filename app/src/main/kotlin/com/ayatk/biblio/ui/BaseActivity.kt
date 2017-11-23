/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import com.ayatk.biblio.App
import com.ayatk.biblio.R
import com.ayatk.biblio.di.ActivityComponent
import com.ayatk.biblio.di.ActivityModule
import com.tomoima.debot.Debot

open class BaseActivity : AppCompatActivity() {

  private var activityComponent: ActivityComponent? = null

  private val debot: Debot by lazy {
    Debot.getInstance()
  }

  fun component(): ActivityComponent {
    if (activityComponent == null) {
      val app = application as App
      activityComponent = app.component().plus(ActivityModule(this))
    }
    return activityComponent as ActivityComponent
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    debot.allowShake(applicationContext)
  }

  override fun onResume() {
    super.onResume()
    debot.startSensor(this)
  }

  override fun onPause() {
    super.onPause()
    debot.stopSensor()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
    if (keyCode == KeyEvent.KEYCODE_MENU) {
      debot.showDebugMenu(this)
    }
    return super.onKeyUp(keyCode, event)
  }

  internal fun initBackToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
      title = toolbar.title
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
      setDisplayShowTitleEnabled(true)
      setHomeButtonEnabled(true)
    }
  }

  internal fun replaceFragment(fragment: Fragment, @IdRes @LayoutRes layoutResId: Int) {
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        .replace(layoutResId, fragment, fragment.javaClass.simpleName)
        .commit()
  }
}
