/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.databinding.FragmentNovelInfoBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.viewmodel.NovelInfoViewModel
import org.parceler.Parcels
import javax.inject.Inject

class NovelInfoFragment : BaseFragment() {

  private lateinit var binding: FragmentNovelInfoBinding

  @Inject
  lateinit var navigator: Navigator

  @Inject
  lateinit var libraryRepository: LibraryRepository

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(arguments?.getParcelable(BUNDLE_ARGS_NOVEL))
  }

  private lateinit var viewModel: NovelInfoViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    binding = FragmentNovelInfoBinding.inflate(inflater, container, false)
    viewModel = NovelInfoViewModel(navigator, libraryRepository, novel)
    binding.viewModel = viewModel

    return binding.root
  }

  override fun onResume() {
    super.onResume()
    viewModel.start()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    component().inject(this)
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): NovelInfoFragment {
      return NovelInfoFragment().apply {
        arguments = Bundle().apply {
          putParcelable(BUNDLE_ARGS_NOVEL, Parcels.wrap(novel))
        }
      }
    }
  }
}
