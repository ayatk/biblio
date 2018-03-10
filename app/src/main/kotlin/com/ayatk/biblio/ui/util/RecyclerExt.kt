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

package com.ayatk.biblio.ui.util

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ayatk.biblio.R
import com.ayatk.biblio.util.ext.drawable

fun <VH : RecyclerView.ViewHolder> RecyclerView.init(
    adapter: RecyclerView.Adapter<VH>,
    scrollListener: RecyclerView.OnScrollListener? = null
) {
  val divider = DividerItemDecoration(context, 1)
  context.drawable(R.drawable.divider).let { divider.setDrawable(it) }

  let {
    it.adapter = adapter
    it.setHasFixedSize(true)
    it.addItemDecoration(divider)
    it.layoutManager = LinearLayoutManager(context)
    if (scrollListener != null) {
      it.addOnScrollListener(scrollListener)
    }
  }
}
