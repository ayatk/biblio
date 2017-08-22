/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
class HttpClientModule {

  private val CACHE_FILE_NAME = "okhttp.cache"
  private val MAX_CACHE_SIZE = (4 * 1024 * 1024).toLong()

  @Singleton
  @Provides
  fun provideHttpClient(context: Context, interceptor: Interceptor): OkHttpClient {
    val cacheDir = File(context.cacheDir, CACHE_FILE_NAME)
    val cache = Cache(cacheDir, MAX_CACHE_SIZE)

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    val c = OkHttpClient
        .Builder()
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .addNetworkInterceptor(StethoInterceptor())

    return c.build()
  }
}
