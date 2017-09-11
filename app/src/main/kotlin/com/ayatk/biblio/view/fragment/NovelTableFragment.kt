/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.fragment

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
import com.ayatk.biblio.view.customview.BindingHolder
import com.ayatk.biblio.view.customview.ObservableListRecyclerAdapter
import com.ayatk.biblio.viewmodel.NovelTableViewModel
import com.ayatk.biblio.viewmodel.NovelTablesViewModel
import org.parceler.Parcels
import javax.inject.Inject

class NovelTableFragment : BaseFragment() {

  @Inject
  lateinit var viewModel: NovelTablesViewModel

  private lateinit var binding: FragmentNovelTableBinding

  private lateinit var novel: Novel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    novel = Parcels.unwrap(arguments.getParcelable("NOVEL"))
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    binding = FragmentNovelTableBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    initRecyclerView()
    return binding.root
  }

  override fun onResume() {
    super.onResume()
    viewModel.start(novel)
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    component().inject(this)
  }

  private fun initRecyclerView() {
    binding.recyclerView.adapter = TableAdapter(context, viewModel.novelTableViewModels)
    binding.recyclerView.setHasFixedSize(true)
    binding.recyclerView.addItemDecoration(DividerItemDecoration(context, 1))
    binding.recyclerView.layoutManager = LinearLayoutManager(context)
  }

  companion object {
    fun newInstance(novel: Novel): NovelTableFragment {
      val bundle = Bundle()
      val fragment = NovelTableFragment()
      bundle.putParcelable("NOVEL", Parcels.wrap(novel))
      fragment.arguments = bundle
      return fragment
    }
  }

  private inner class TableAdapter constructor(
      context: Context, list: ObservableList<NovelTableViewModel>) :
      ObservableListRecyclerAdapter<NovelTableViewModel, BindingHolder<ViewTableItemBinding>>(
          context, list) {

    init {
      setHasStableIds(false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): BindingHolder<ViewTableItemBinding> {
      return BindingHolder(context, parent, layout.view_table_item)
    }

    override fun onBindViewHolder(holder: BindingHolder<ViewTableItemBinding>, position: Int) {
      val viewModel = getItem(position)
      val itemBinding = holder.binding
      itemBinding.viewModel = viewModel
      itemBinding.executePendingBindings()
    }
  }
}
