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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.os.bundleOf
import com.ayatk.biblio.databinding.FragmentRankingListBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.RankingType
import com.ayatk.biblio.ui.ranking.item.RankingItem
import com.ayatk.biblio.ui.util.helper.navigateToDetail
import com.ayatk.biblio.ui.util.init
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.observe
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class RankingListFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: FragmentRankingListBinding

  private val viewModel: RankingViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(RankingViewModel::class.java)
  }

  private val rankingType: RankingType by lazy {
    arguments?.getSerializable(BUNDLE_ARGS_RANKING_TYPE)!! as RankingType
  }

  private val groupAdapter = GroupAdapter<ViewHolder>()
  private val onClickListener = { novel: Novel ->
    context!!.navigateToDetail(novel)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentRankingListBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.list.init(groupAdapter)

    viewModel.rankings(rankingType).observe(this) { result ->
      when (result) {
        is Result.Success -> {
          val rankings = result.data
          groupAdapter.update(rankings.map {
            RankingItem(it, onClickListener)
          })
          binding.progress.setVisible(rankings.isEmpty())
          binding.list.setVisible(rankings.isNotEmpty())
        }
        is Result.Failure -> Timber.e(result.e)
      }
    }
  }

  companion object {
    private const val BUNDLE_ARGS_RANKING_TYPE = "ranking_type"

    fun newInstance(rankingType: RankingType): Fragment =
      RankingListFragment().apply {
        arguments = bundleOf(BUNDLE_ARGS_RANKING_TYPE to rankingType)
      }
  }
}
