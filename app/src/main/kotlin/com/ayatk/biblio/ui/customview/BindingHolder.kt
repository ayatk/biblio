/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.customview

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup

class BindingHolder<out T : ViewDataBinding>(
    @NonNull context: Context,
    @NonNull parent: ViewGroup,
    @LayoutRes layoutResId: Int
) : ViewHolder(LayoutInflater.from(context).inflate(layoutResId, parent, false)) {

  val binding: T = DataBindingUtil.bind(itemView)
}
