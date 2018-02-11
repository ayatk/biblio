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
import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.FragmentLibraryBinding
import com.ayatk.biblio.databinding.ViewLibraryItemBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.ui.util.customview.BindingHolder
import com.ayatk.biblio.ui.util.customview.ObservableListRecyclerAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LibraryFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private val viewModel: LibraryViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(LibraryViewModel::class.java)
  }

  lateinit var binding: FragmentLibraryBinding

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

  override fun onResume() {
    super.onResume()
    viewModel.start(false)
  }

  private fun initRecyclerView() {
    val divider = DividerItemDecoration(context, 1)
    ContextCompat.getDrawable(context!!, R.drawable.divider)?.let { divider.setDrawable(it) }

    binding.recyclerView.apply {
      adapter = LibraryAdapter(context, viewModel.libraryViewModels)
      setHasFixedSize(true)
      addItemDecoration(divider)
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    fun newInstance(): LibraryFragment = LibraryFragment()
  }

  private inner class LibraryAdapter constructor(
      context: Context, list: ObservableList<LibraryItemViewModel>
  ) : ObservableListRecyclerAdapter<LibraryItemViewModel, BindingHolder<ViewLibraryItemBinding>>(context, list) {

    init {
      setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BindingHolder<ViewLibraryItemBinding> =
        BindingHolder(context, parent, R.layout.view_library_item)

    override fun onBindViewHolder(holder: BindingHolder<ViewLibraryItemBinding>, position: Int) {
      holder.binding.apply {
        viewModel = getItem(position)
        executePendingBindings()
      }
    }

    override fun getItemId(position: Int): Long =
        getItem(position).library.id
  }
}
