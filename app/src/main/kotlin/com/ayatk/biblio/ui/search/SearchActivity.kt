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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivitySearchBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.search.item.SearchResultItem
import com.ayatk.biblio.ui.util.helper.navigateToDetail
import com.ayatk.biblio.ui.util.init
import com.ayatk.biblio.ui.util.initBackToolbar
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.observe
import com.xwray.groupie.GroupAdapter
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

  private val groupAdapter = GroupAdapter<ViewHolder>()
  private val onItemClickListener = { novel: Novel ->
    navigateToDetail(novel)
  }
  private val onDownloadClickListener: (Novel) -> Unit = { novel: Novel ->
    viewModel.saveNovel(novel)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit)
    binding.setLifecycleOwner(this)
    binding.viewModel = viewModel

    initBackToolbar(binding.toolbar)

    val scrollListener = object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          searchView.clearFocus()
        }
      }
    }

    binding.searchResult.init(groupAdapter, scrollListener)

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
          groupAdapter.update(novels.map {
            SearchResultItem(it, onItemClickListener, onDownloadClickListener)
          })
          if (binding.searchResult.isGone) {
            binding.searchResult.isVisible = novels.isNotEmpty()
          }
        }
        is Result.Failure -> Timber.e(result.e)
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

  companion object {
    fun createIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
  }
}
