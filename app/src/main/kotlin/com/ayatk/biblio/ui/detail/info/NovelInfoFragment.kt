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
import androidx.os.bundleOf
import com.ayatk.biblio.databinding.FragmentNovelInfoBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.repository.library.LibraryDataSource
import dagger.android.support.DaggerFragment
import org.parceler.Parcels
import javax.inject.Inject

class NovelInfoFragment : DaggerFragment() {

  private lateinit var binding: FragmentNovelInfoBinding

  @Inject
  lateinit var libraryDataSource: LibraryDataSource

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(arguments?.getParcelable(BUNDLE_ARGS_NOVEL))
  }

  private lateinit var viewModel: NovelInfoViewModel

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNovelInfoBinding.inflate(inflater, container, false)
    viewModel = NovelInfoViewModel(libraryDataSource, novel)
    binding.viewModel = viewModel

    return binding.root
  }

  override fun onResume() {
    super.onResume()
    viewModel.start()
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): NovelInfoFragment {
      return NovelInfoFragment().apply {
        arguments = bundleOf(BUNDLE_ARGS_NOVEL to Parcels.wrap(novel))
      }
    }
  }
}
