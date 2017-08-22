/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import com.ayatk.biblio.di.scope.ActivityScope
import com.ayatk.biblio.view.activity.MainActivity
import com.ayatk.biblio.view.activity.NovelBodyActivity
import com.ayatk.biblio.view.activity.NovelDetailActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

  fun inject(activity: MainActivity)

  fun inject(activity: NovelDetailActivity)

  fun inject(activity: NovelBodyActivity)

  fun plus(module: FragmentModule): FragmentComponent
}
