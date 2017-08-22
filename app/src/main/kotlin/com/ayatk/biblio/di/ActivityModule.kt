/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import android.support.v7.app.AppCompatActivity
import com.ayatk.biblio.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

  @Provides
  @ActivityScope
  fun provideActivity(): AppCompatActivity = activity
}
