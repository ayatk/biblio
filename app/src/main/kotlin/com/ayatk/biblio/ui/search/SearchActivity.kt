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

package com.ayatk.biblio.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivitySearchBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.search.item.SearchResultItem
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.ui.util.initBackToolbar
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.drawable
import com.ayatk.biblio.util.ext.observe
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private val viewModel: SearchViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
  }

  private val binding: ActivitySearchBinding by lazy {
    DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
  }

  private lateinit var searchView: SearchView

  private val searchSection = Section()
  private val onItemClickListener = { novel: Novel ->
    Navigator.navigateToDetail(this, novel)
  }
  private val onDownloadClickListener: (Novel) -> Unit = { novel: Novel ->
    viewModel.saveNovel(novel)
        .subscribe()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit)
    binding.setLifecycleOwner(this)
    binding.viewModel = viewModel

    initBackToolbar(binding.toolbar)

    initRecyclerView()

    binding.drawerLayout.addDrawerListener(
        object : ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.drawer_open,
            R.string.drawer_close
        ) {
          override fun onDrawerStateChanged(newState: Int) {
            super.onDrawerStateChanged(newState)
            Timber.d(newState.toString())
            if (newState != DrawerLayout.STATE_IDLE) {
              searchView.clearFocus()
            }
          }
        }
    )

    viewModel.result.observe(this) { result ->
      when (result) {
        is Result.Success -> {
          val novels = result.data
          searchSection.update(novels.map {
            SearchResultItem(it, onItemClickListener, onDownloadClickListener)
          })
          binding.searchResult.setVisible(novels.isNotEmpty())
        }
        is Result.Failure -> {
          Timber.e(result.e)
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.menu_search, menu)

    val menuItem = menu.findItem(R.id.action_search)
    menuItem.setOnActionExpandListener(
        object : MenuItem.OnActionExpandListener {
          override fun onMenuItemActionExpand(item: MenuItem): Boolean = true

          override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
            onBackPressed()
            return false
          }
        }
    )
    menuItem.expandActionView()

    searchView = menuItem.actionView as SearchView
    searchView.setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
          override fun onQueryTextSubmit(query: String): Boolean = onQueryTextChange(query)

          override fun onQueryTextChange(newText: String): Boolean {
            viewModel.setQuery(newText)
            return false
          }
        }
    )
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.action_filter) {
      searchView.clearFocus()
      binding.drawerLayout.openDrawer(GravityCompat.END)
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

  private fun initRecyclerView() {
    val divider = DividerItemDecoration(this, 1)
    this.drawable(R.drawable.divider).let { divider.setDrawable(it) }

    binding.searchResult.apply {
      adapter = GroupAdapter<ViewHolder>().apply {
        add(searchSection)
      }
      setHasFixedSize(true)
      addItemDecoration(divider)
      layoutManager = LinearLayoutManager(context)
      addOnScrollListener(
          object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
              if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                searchView.clearFocus()
              }
            }
          }
      )
    }
  }

  companion object {
    fun createIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
  }
}
