/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.customview

import android.content.Context
import android.support.annotation.UiThread
import android.support.v7.widget.RecyclerView

abstract class ArrayRecyclerAdapter<T, VH : RecyclerView.ViewHolder>
constructor(val context: Context, protected val list: MutableList<T>) : RecyclerView.Adapter<VH>() {

  @UiThread
  fun reset(items: Collection<T>) {
    clear()
    addAll(items)
    notifyDataSetChanged()
  }

  fun clear() {
    list.clear()
  }

  fun addAll(items: Collection<T>) {
    list.addAll(items)
  }

  fun getItem(position: Int): T {
    return list[position]
  }

  fun addItem(item: T) {
    list.add(item)
  }

  fun addAll(position: Int, items: Collection<T>) {
    list.addAll(position, items)
  }

  @UiThread
  fun addAllWithNotify(items: Collection<T>) {
    val position = itemCount
    addAll(items)
    notifyItemInserted(position)
  }

  override fun getItemCount(): Int {
    return list.size
  }
}
