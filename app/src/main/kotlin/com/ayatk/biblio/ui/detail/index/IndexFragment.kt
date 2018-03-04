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

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.FragmentIndexBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.detail.index.item.IndexItem
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.drawable
import com.ayatk.biblio.util.ext.observe
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerFragment
import org.parceler.Parcels
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
    Parcels.unwrap<Novel>(arguments?.getParcelable(BUNDLE_ARGS_NOVEL))
  }

  private val indexSection = Section()
  private val onClickListener = { index: Index ->
    Navigator.navigateToDetail(context!!, index.novel)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentIndexBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)
    initRecyclerView()

    // 初期読み込み
    viewModel.getIndex(novel)

    viewModel.indexLiveData.observe(this, { result ->
      when (result) {
        is Result.Success -> {
          val indexes = result.data
          indexSection.update(indexes.map {
            IndexItem(it, onClickListener)
          })
          binding.recyclerView.setVisible(indexes.isNotEmpty())
        }
        is Result.Failure -> {
          Timber.e(result.e)
        }
      }
    })

    return binding.root
  }

  private fun initRecyclerView() {
    val divider = DividerItemDecoration(context, 1)
    context!!.drawable(R.drawable.divider)
        .let { divider.setDrawable(it) }

    binding.recyclerView.apply {
      adapter = GroupAdapter<ViewHolder>().apply {
        add(indexSection)
      }
      setHasFixedSize(true)
      addItemDecoration(divider)
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): IndexFragment {
      return IndexFragment().apply {
        arguments = bundleOf(BUNDLE_ARGS_NOVEL to Parcels.wrap(novel))
      }
    }
  }
}
