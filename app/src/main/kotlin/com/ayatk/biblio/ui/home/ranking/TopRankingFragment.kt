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

package com.ayatk.biblio.ui.home.ranking

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.databinding.FragmentRankingBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.ui.util.customview.RankingTopCellView
import com.ayatk.biblio.util.Analytics
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.observe
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class TopRankingFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private val viewModel: TopRankingViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(TopRankingViewModel::class.java)
  }

  lateinit var binding: FragmentRankingBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycle.addObserver(Analytics.ScreenLog(Analytics.Screen.TOP_RANKING))
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentRankingBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)

    bind(viewModel.daily, binding.daily)
    bind(viewModel.weekly, binding.weekly)
    bind(viewModel.monthly, binding.monthly)
    bind(viewModel.quarter, binding.quarter)
    bind(viewModel.all, binding.all)

    return binding.root
  }

  private fun bind(data: LiveData<Result<List<Ranking>>>, bind: RankingTopCellView) {
    data.observe(this, { result ->
      when (result) {
        is Result.Success -> bind.setRankings(result.data)
        is Result.Failure -> Timber.e(result.e)
      }
    })
  }

  companion object {
    fun newInstance(): TopRankingFragment = TopRankingFragment()
  }
}
