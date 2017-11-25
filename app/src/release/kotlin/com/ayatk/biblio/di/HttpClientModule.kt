/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
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

  private val CACHE_FILE_NAME = "okhttp.cache"
  private val MAX_CACHE_SIZE = (4 * 1024 * 1024).toLong()

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

    val c = OkHttpClient
        .Builder()
        .addInterceptor(addUserAgentInterceptor)
        .cache(cache)

    return c.build()
  }
}
