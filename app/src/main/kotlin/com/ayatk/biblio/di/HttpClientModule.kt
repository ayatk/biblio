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
import com.ayatk.biblio.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
class HttpClientModule {

  companion object {
    private const val CACHE_FILE_NAME = "biblio.cache"
    private const val MAX_CACHE_SIZE = (4 * 1024 * 1024).toLong()
  }

  private val addUserAgentInterceptor = { chain: Chain ->
    val builder = chain.request().newBuilder()
    builder.addHeader("User-Agent", "BiblioAndroidApp/" + BuildConfig.VERSION_NAME)
    chain.proceed(builder.build())
  }

  @Singleton
  @Provides
  fun provideHttpClient(application: Application): OkHttpClient {
    val cacheDir = File(application.cacheDir, CACHE_FILE_NAME)
    val cache = Cache(cacheDir, MAX_CACHE_SIZE)

    return OkHttpClient
        .Builder()
        .addInterceptor(addUserAgentInterceptor)
        .cache(cache)
        .build()
  }
}
