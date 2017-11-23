/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui

import android.support.v4.app.Fragment
import com.ayatk.biblio.di.FragmentComponent
import com.ayatk.biblio.di.FragmentModule

open class BaseFragment : Fragment() {

  private var fragmentComponent: FragmentComponent? = null

  fun component(): FragmentComponent {
    if (fragmentComponent != null) {
      return fragmentComponent as FragmentComponent
    }

    val activity = activity
    if (activity is BaseActivity) {
      fragmentComponent = activity.component().plus(FragmentModule(this))
      return fragmentComponent as FragmentComponent
    } else {
      throw IllegalStateException(
          "The activity of this fragment is not an instance of BaseActivity")
    }
  }
}
