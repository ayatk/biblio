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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.databinding.FragmentRankingBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TopRankingFragment : DaggerFragment() {

  lateinit var binding: FragmentRankingBinding

  @Inject
  lateinit var viewModel: TopRankingViewModel

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    binding = FragmentRankingBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    viewModel.start()

    return binding.root
  }

  override fun onDetach() {
    viewModel.destroy()
    super.onDetach()
  }

  companion object {
    fun newInstance(): TopRankingFragment = TopRankingFragment()
  }
}
