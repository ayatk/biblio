/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import com.ayatk.biblio.di.scope.ActivityScope
import com.ayatk.biblio.ui.body.NovelBodyActivity
import com.ayatk.biblio.ui.detail.NovelDetailActivity
import com.ayatk.biblio.ui.home.HomeActivity
import com.ayatk.biblio.ui.search.SearchActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

  fun inject(activity: HomeActivity)

  fun inject(activity: NovelDetailActivity)

  fun inject(activity: NovelBodyActivity)

  fun inject(activity: SearchActivity)

  fun plus(module: FragmentModule): FragmentComponent
}
