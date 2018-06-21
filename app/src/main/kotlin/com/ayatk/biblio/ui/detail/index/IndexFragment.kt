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

package com.ayatk.biblio.ui.detail.index

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.ayatk.biblio.databinding.FragmentIndexBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.detail.index.item.IndexItem
import com.ayatk.biblio.ui.util.helper.navigateToEpisode
import com.ayatk.biblio.ui.util.init
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.observe
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class IndexFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private val viewModel: IndexViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(IndexViewModel::class.java)
  }

  private lateinit var binding: FragmentIndexBinding

  private val novel: Novel by lazy {
    arguments?.getSerializable(BUNDLE_ARGS_NOVEL) as Novel
  }

  private val indexSection = Section()
  private val onClickListener = { index: Index ->
    context!!.navigateToEpisode(index.novel, index.page)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentIndexBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)

    binding.recyclerView.init(GroupAdapter<ViewHolder>().apply {
      add(indexSection)
    })

    viewModel.getIndex(novel).observe(this, { result ->
      when (result) {
        is Result.Success -> {
          val indexes = result.data
          indexSection.update(indexes.map {
            IndexItem(it, onClickListener)
          })
          binding.recyclerView.setVisible(indexes.isNotEmpty())
        }
        is Result.Failure -> Timber.e(result.e)
      }
    })

    return binding.root
  }

  companion object {
    private const val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): IndexFragment =
        IndexFragment().apply {
          arguments = bundleOf(BUNDLE_ARGS_NOVEL to novel)
        }
  }
}
