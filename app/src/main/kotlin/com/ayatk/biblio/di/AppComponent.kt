/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, HttpClientModule::class))
interface AppComponent {

  fun plus(module: ActivityModule): ActivityComponent
}
