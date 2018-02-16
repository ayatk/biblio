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

package com.ayatk.biblio.ui.home.library

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.R
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.databinding.FragmentLibraryBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.home.library.item.LibraryItem
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.drawable
import com.ayatk.biblio.util.ext.observe
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class LibraryFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  @Inject
  lateinit var defaultPrefs: DefaultPrefs

  private val viewModel: LibraryViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(LibraryViewModel::class.java)
  }

  lateinit var binding: FragmentLibraryBinding

  private val librarySection = Section()
  private val onClickListener = { novel: Novel ->
    Navigator.navigateToDetail(context!!, novel)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentLibraryBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)
    binding.viewModel = viewModel

    // 色設定
    binding.refresh.setColorSchemeResources(R.color.app_blue)

    initRecyclerView()

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initRecyclerView()

    viewModel.libraries.observe(this, { result ->
      when (result) {
        is Result.Success -> {
          val libraries = result.data
          librarySection.update(libraries.map {
            LibraryItem(it, defaultPrefs, onClickListener)
          })
          binding.emptyView.setVisible(libraries.isEmpty())
          binding.recyclerView.setVisible(libraries.isNotEmpty())
        }
        is Result.Failure -> {
          Timber.e(result.e)
        }
      }
    })
  }

  private fun initRecyclerView() {
    val divider = DividerItemDecoration(context, 1)
    context!!.drawable(R.drawable.divider)
        .let { divider.setDrawable(it) }

    binding.recyclerView.apply {
      adapter = GroupAdapter<ViewHolder>().apply {
        add(librarySection)
      }
      setHasFixedSize(true)
      addItemDecoration(divider)
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    fun newInstance(): LibraryFragment = LibraryFragment()
  }
}
