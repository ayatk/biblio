/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.R
import com.ayatk.biblio.R.layout
import com.ayatk.biblio.databinding.ActivitySearchBinding
import com.ayatk.biblio.databinding.ViewSearchResultItemBinding
import com.ayatk.biblio.ui.customview.BindingHolder
import com.ayatk.biblio.ui.customview.ObservableListRecyclerAdapter
import com.ayatk.biblio.viewmodel.SearchResultItemViewModel
import com.ayatk.biblio.viewmodel.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class SearchActivity : BaseActivity() {

  @Inject
  lateinit var viewModel: SearchViewModel

  private val binding: ActivitySearchBinding by lazy {
    DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
  }

  private lateinit var searchView: SearchView

  override fun onCreate(savedInstanceState: Bundle?) {
    component().inject(this)
    super.onCreate(savedInstanceState)
    overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit)

    initBackToolbar(binding.toolbar)

    viewModel.searchResultVisibility
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ visibility -> binding.searchResult.visibility = visibility })

    binding.drawerLayout.addDrawerListener(
        object : ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open,
            R.string.drawer_close) {

          override fun onDrawerClosed(view: View) {
            super.onDrawerClosed(view)
          }

          override fun onDrawerOpened(drawerView: View) {
            super.onDrawerOpened(drawerView)
            searchView.clearFocus()
          }
        }
    )

    binding.searchResult.apply {
      adapter = SearchResultAdapter(context, viewModel.searchResult)
      addItemDecoration(DividerItemDecoration(context, 1))
      layoutManager = LinearLayoutManager(context)
      addOnScrollListener(object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
          if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            searchView.clearFocus()
          }
        }
      })
    }
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
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean = onQueryTextChange(query)

      override fun onQueryTextChange(newText: String): Boolean {
        viewModel.search(newText)
        return false
      }
    })
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.action_filter) {
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

  private inner class SearchResultAdapter constructor(
      context: Context, list: ObservableList<SearchResultItemViewModel>) :
      ObservableListRecyclerAdapter<SearchResultItemViewModel, BindingHolder<ViewSearchResultItemBinding>>(
          context, list) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): BindingHolder<ViewSearchResultItemBinding> {
      return BindingHolder(context, parent, layout.view_search_result_item)
    }

    override fun onBindViewHolder(
        holder: BindingHolder<ViewSearchResultItemBinding>, position: Int) {
      holder.binding.apply {
        viewModel = getItem(position)
        executePendingBindings()
      }
    }
  }
}
