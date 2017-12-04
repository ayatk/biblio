/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.detail.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        arguments = Bundle().apply {
          putParcelable(
              BUNDLE_ARGS_NOVEL, Parcels.wrap(novel)
          )
        }
      }
    }
  }
}
