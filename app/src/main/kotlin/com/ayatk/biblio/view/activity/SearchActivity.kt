/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivitySearchBinding
import com.ayatk.biblio.view.fragment.SearchFragment


class SearchActivity : BaseActivity() {

  private lateinit var searchView: SearchView
  private val binding: ActivitySearchBinding by lazy {
    DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    overridePendingTransition(0, R.anim.activity_fade_exit)

    initBackToolbar(binding.toolbar)
    replaceFragment(SearchFragment.newInstance(), R.id.content_view)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.menu_search, menu)

    val menuItem = menu.findItem(R.id.action_search)
    menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
      override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
      }

      override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        onBackPressed()
        return false
      }
    })
    menuItem.expandActionView()

    searchView = menuItem.actionView as SearchView
    searchView.apply {
      setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
          return onQueryTextChange(query)
        }

        override fun onQueryTextChange(newText: String): Boolean {
          return true
        }
      })
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.action_filter) {
      binding.drawerLayout.openDrawer(GravityCompat.END)
      searchView.clearFocus()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(0, R.anim.activity_fade_exit)
  }

  override fun onBackPressed() {
    if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
      binding.drawerLayout.closeDrawer(GravityCompat.END)
    } else {
      finish()
    }
  }

  companion object {
    fun createIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
  }
}
