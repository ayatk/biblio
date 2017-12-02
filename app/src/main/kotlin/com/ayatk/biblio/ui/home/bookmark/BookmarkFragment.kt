/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.home.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.databinding.FragmentBookmarkBinding
import dagger.android.support.DaggerFragment

class BookmarkFragment : DaggerFragment() {

  lateinit var binding: FragmentBookmarkBinding

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentBookmarkBinding.inflate(inflater, container, false)
    return binding.root
  }

  companion object {
    fun newInstance(): BookmarkFragment = BookmarkFragment()
  }
}
