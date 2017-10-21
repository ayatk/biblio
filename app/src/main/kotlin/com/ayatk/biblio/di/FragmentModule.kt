/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.ayatk.biblio.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

  @Provides
  @FragmentScope
  fun provideFragmentManager(): FragmentManager = fragment.fragmentManager
}
