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

package com.ayatk.biblio.ui.detail.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.ayatk.biblio.databinding.FragmentInfoBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Novel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfoFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private val viewModel: InfoViewModel by lazy {
    ViewModelProvider(this, viewModelFactory).get(InfoViewModel::class.java)
  }

  private lateinit var binding: FragmentInfoBinding

  private val novel: Novel by lazy {
    arguments?.getSerializable(BUNDLE_ARGS_NOVEL) as Novel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.novel = novel
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentInfoBinding.inflate(inflater, container, false)

    lifecycle.addObserver(viewModel)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    return binding.root
  }

  companion object {
    private const val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): InfoFragment =
      InfoFragment().apply {
        arguments = bundleOf(BUNDLE_ARGS_NOVEL to novel)
      }
  }
}
