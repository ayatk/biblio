/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.home.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayatk.biblio.databinding.FragmentRankingBinding
import dagger.android.support.DaggerFragment

class RankingFragment : DaggerFragment() {

  lateinit var binding: FragmentRankingBinding

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentRankingBinding.inflate(inflater, container, false)
    return binding.root
  }

  companion object {
    fun newInstance(): RankingFragment = RankingFragment()
  }
}
