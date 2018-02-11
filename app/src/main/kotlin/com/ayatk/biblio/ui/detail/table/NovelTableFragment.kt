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

package com.ayatk.biblio.ui.detail.table

import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.ayatk.biblio.R
import com.ayatk.biblio.R.layout
import com.ayatk.biblio.databinding.FragmentNovelTableBinding
import com.ayatk.biblio.databinding.ViewTableItemBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.util.customview.BindingHolder
import com.ayatk.biblio.ui.util.customview.ObservableListRecyclerAdapter
import dagger.android.support.DaggerFragment
import org.parceler.Parcels
import javax.inject.Inject

class NovelTableFragment : DaggerFragment() {

  @Inject
  lateinit var viewModel: NovelTableViewModel

  private lateinit var binding: FragmentNovelTableBinding

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(arguments?.getParcelable(BUNDLE_ARGS_NOVEL)
    )
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNovelTableBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    initRecyclerView()
    return binding.root
  }

  override fun onResume() {
    super.onResume()
    viewModel.start(novel)
  }

  override fun onDestroy() {
    viewModel.destroy()
    super.onDestroy()
  }

  private fun initRecyclerView() {
    val divider = DividerItemDecoration(context, 1)
    ContextCompat.getDrawable(context!!, R.drawable.divider)?.let { divider.setDrawable(it) }

    binding.recyclerView.apply {
      adapter = TableAdapter(context, viewModel.novelTableViewModels)
      setHasFixedSize(true)
      addItemDecoration(divider)
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): NovelTableFragment {
      return NovelTableFragment().apply {
        arguments = bundleOf(BUNDLE_ARGS_NOVEL to Parcels.wrap(novel))
      }
    }
  }

  private inner class TableAdapter constructor(
      context: Context, list: ObservableList<NovelTableItemViewModel>
  ) :
      ObservableListRecyclerAdapter<NovelTableItemViewModel, BindingHolder<ViewTableItemBinding>>(
          context, list
      ) {

    init {
      setHasStableIds(false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BindingHolder<ViewTableItemBinding> =
        BindingHolder(context, parent, layout.view_table_item)

    override fun onBindViewHolder(holder: BindingHolder<ViewTableItemBinding>, position: Int) {
      holder.binding.apply {
        viewModel = getItem(position)
        executePendingBindings()
      }
    }
  }
}
