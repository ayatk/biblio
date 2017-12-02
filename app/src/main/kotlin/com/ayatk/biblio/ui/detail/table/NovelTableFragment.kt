/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.detail.table

import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    Parcels.unwrap<Novel>(
        arguments?.getParcelable(
            BUNDLE_ARGS_NOVEL
        )
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

  private fun initRecyclerView() {
    binding.recyclerView.apply {
      adapter = TableAdapter(context, viewModel.novelTableViewModels)
      setHasFixedSize(true)
      addItemDecoration(DividerItemDecoration(context, 1))
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): NovelTableFragment {
      return NovelTableFragment().apply {
        arguments = Bundle().apply {
          putParcelable(
              BUNDLE_ARGS_NOVEL, Parcels.wrap(novel)
          )
        }
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
