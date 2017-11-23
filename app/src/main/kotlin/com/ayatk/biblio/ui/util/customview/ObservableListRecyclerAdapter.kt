/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.util.customview

import android.content.Context
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

abstract class ObservableListRecyclerAdapter<T, VH : RecyclerView.ViewHolder>(
    context: Context, list: ObservableList<T>) : ArrayRecyclerAdapter<T, VH>(context, list) {

  init {

    list.addOnListChangedCallback(
        object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
          override fun onChanged(contributorViewModels: ObservableList<T>) {
            notifyDataSetChanged()
          }

          override fun onItemRangeChanged(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
            notifyItemRangeChanged(i, i1)
          }

          override fun onItemRangeInserted(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
            notifyItemRangeInserted(i, i1)
          }

          override fun onItemRangeMoved(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int, i2: Int) {
            notifyItemMoved(i, i1)
          }

          override fun onItemRangeRemoved(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
            notifyItemRangeRemoved(i, i1)
          }
        })
  }
}
