/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.databinding.FragmentNovelBodyBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.view.activity.NovelBodyActivity
import com.ayatk.biblio.viewmodel.NovelBodyViewModel
import org.parceler.Parcels
import javax.inject.Inject

class NovelBodyFragment : BaseFragment() {

  @Inject
  lateinit var viewModel: NovelBodyViewModel

  private lateinit var binding: FragmentNovelBodyBinding

  private lateinit var novel: Novel

  private var page = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    novel = Parcels.unwrap(arguments.getParcelable(BUNDLE_ARGS_NOVEL))
    page = arguments.getInt(BUNDLE_ARGS_NOVEL_PAGE)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    binding = FragmentNovelBodyBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    (activity as NovelBodyActivity).binding.toolbar.title = viewModel.novelBody.subtitle

    return binding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    component().inject(this)
  }

  override fun onResume() {
    super.onResume()
    viewModel.start(novel, page)
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"
    private val BUNDLE_ARGS_NOVEL_PAGE = "NOVEL_PAGE"

    fun newInstance(novel: Novel, page: Int): NovelBodyFragment {
      return NovelBodyFragment().apply {
        arguments = Bundle().apply {
          putParcelable(BUNDLE_ARGS_NOVEL, Parcels.wrap(novel))
          putInt(BUNDLE_ARGS_NOVEL_PAGE, page)
        }
      }
    }
  }
}
