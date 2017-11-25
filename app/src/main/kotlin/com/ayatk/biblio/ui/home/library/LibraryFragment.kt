/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.home.library

import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.FragmentLibraryBinding
import com.ayatk.biblio.databinding.ViewLibraryItemBinding
import com.ayatk.biblio.ui.util.customview.BindingHolder
import com.ayatk.biblio.ui.util.customview.ObservableListRecyclerAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LibraryFragment : DaggerFragment() {

  lateinit var binding: FragmentLibraryBinding

  @Inject
  lateinit var viewModel: LibraryViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    binding = FragmentLibraryBinding.inflate(inflater, container, false)
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

  override fun onDestroy() {
    super.onDestroy()
    viewModel.destroy()
  }

  private fun initRecyclerView() {
    binding.recyclerView.apply {
      adapter = LibraryAdapter(context, viewModel.libraryViewModels)
      setHasFixedSize(true)
      addItemDecoration(DividerItemDecoration(context, 1))
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    fun newInstance(): LibraryFragment = LibraryFragment()
  }

  private inner class LibraryAdapter constructor(
      context: Context, list: ObservableList<LibraryItemViewModel>) :
      ObservableListRecyclerAdapter<LibraryItemViewModel, BindingHolder<ViewLibraryItemBinding>>(
          context, list) {

    init {
      setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): BindingHolder<ViewLibraryItemBinding>
        = BindingHolder(context, parent, R.layout.view_library_item)

    override fun onBindViewHolder(holder: BindingHolder<ViewLibraryItemBinding>, position: Int) {
      holder.binding.apply {
        viewModel = getItem(position)
        executePendingBindings()
      }
    }

    override fun getItemId(position: Int): Long
        = getItem(position).library.id
  }
}
