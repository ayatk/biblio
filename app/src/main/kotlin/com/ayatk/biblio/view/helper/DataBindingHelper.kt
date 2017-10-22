/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.helper

import android.databinding.BindingConversion
import android.graphics.drawable.ColorDrawable

object DataBindingHelper {

  @JvmStatic
  @BindingConversion
  fun convertColorToDrawable(color: Int): ColorDrawable = ColorDrawable(color)
}
