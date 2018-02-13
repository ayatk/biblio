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

package com.ayatk.biblio.di

import android.app.Application
import android.net.ConnectivityManager
import androidx.content.systemService
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.data.narou.util.HtmlUtil
import com.ayatk.biblio.model.OrmaDatabase
import com.ayatk.biblio.util.rx.AppSchedulerProvider
import com.ayatk.biblio.util.rx.SchedulerProvider
import com.github.gfx.android.orma.AccessThreadConstraint
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

  @Provides
  fun provideConnectivityManager(application: Application): ConnectivityManager =
      application.systemService<ConnectivityManager>()

  @Singleton
  @Provides
  fun provideDefaultPrefs(application: Application): DefaultPrefs =
      DefaultPrefs.get(application)

  @Singleton
  @Provides
  fun provideOrmaDatabase(application: Application): OrmaDatabase {
    return OrmaDatabase.builder(application)
        .writeOnMainThread(AccessThreadConstraint.FATAL)
        .readOnMainThread(AccessThreadConstraint.FATAL)
        .build()
  }

  @Singleton
  @Provides
  fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

  @Singleton
  @Provides
  fun provideHtmlUtil(): HtmlUtil = HtmlUtil()
}
