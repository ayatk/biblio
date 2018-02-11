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

package com.ayatk.biblio.ui.ranking

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.R
import com.ayatk.biblio.data.narou.entity.enums.RankingType
import com.ayatk.biblio.databinding.FragmentRankingListBinding
import com.ayatk.biblio.databinding.ViewRankingListItemBinding
import com.ayatk.biblio.ui.util.customview.BindingHolder
import com.ayatk.biblio.ui.util.customview.ObservableListRecyclerAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RankingListFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: FragmentRankingListBinding

  private val viewModel: RankingViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(RankingViewModel::class.java)
  }

  private val rankingType: RankingType by lazy {
    arguments?.getSerializable(ARG_RANKING_TYPE)!! as RankingType
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentRankingListBinding.inflate(inflater, container, false)

    viewModel.onCreate(rankingType)

    val divider = DividerItemDecoration(context, 1)
    ContextCompat.getDrawable(context!!, R.drawable.divider)?.let { divider.setDrawable(it) }

    binding.list.apply {
      adapter = RankingAdapter(context, viewModel.rankingItemViewModels)
      setHasFixedSize(true)
      addItemDecoration(divider)
      layoutManager = LinearLayoutManager(context)
    }

    return binding.root
  }

  private class RankingAdapter(
      context: Context, list: ObservableList<RankingItemViewModel>
  ) : ObservableListRecyclerAdapter<RankingItemViewModel, BindingHolder<ViewRankingListItemBinding>>(
      context, list
  ) {

    init {
      setHasStableIds(false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BindingHolder<ViewRankingListItemBinding> =
        BindingHolder(context, parent, R.layout.view_ranking_list_item)

    override fun onBindViewHolder(
        holder: BindingHolder<ViewRankingListItemBinding>, position: Int
    ) {
      holder.binding.apply {
        viewModel = getItem(position)
        executePendingBindings()
      }
    }
  }

  companion object {
    private const val ARG_RANKING_TYPE = "ranking_type"

    fun newInstance(rankingType: RankingType): Fragment {
      return RankingListFragment().apply {
        arguments = Bundle().apply { putSerializable(ARG_RANKING_TYPE, rankingType) }
      }
    }
  }
}
