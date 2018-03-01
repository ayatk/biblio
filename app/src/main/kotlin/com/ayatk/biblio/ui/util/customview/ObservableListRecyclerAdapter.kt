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

package com.ayatk.biblio.ui.util.customview

import android.content.Context
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

abstract class ObservableListRecyclerAdapter<T, VH : RecyclerView.ViewHolder>(
    context: Context,
    list: ObservableList<T>
) : ArrayRecyclerAdapter<T, VH>(context, list) {

  init {

    list.addOnListChangedCallback(
        object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
          override fun onChanged(contributorViewModels: ObservableList<T>) {
            notifyDataSetChanged()
          }

          override fun onItemRangeChanged(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int
          ) {
            notifyItemRangeChanged(i, i1)
          }

          override fun onItemRangeInserted(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int
          ) {
            notifyItemRangeInserted(i, i1)
          }

          override fun onItemRangeMoved(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int, i2: Int
          ) {
            notifyItemMoved(i, i1)
          }

          override fun onItemRangeRemoved(
              contributorViewModels: ObservableList<T>, i: Int, i1: Int
          ) {
            notifyItemRangeRemoved(i, i1)
          }
        }
    )
  }
}
