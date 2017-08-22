/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.util

import android.net.ConnectivityManager
import com.ayatk.biblio.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor
@Inject constructor(var connectivityManager: ConnectivityManager) : Interceptor {

  val UA = "Biblio/${BuildConfig.VERSION_NAME}${if (BuildConfig.DEBUG) "-debug" else ""} " +
      "build ${BuildConfig.BUILD_NUM} (${BuildConfig.GIT_SHA})"

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val r = chain.request().newBuilder()

    if (isConnected()) {
      val maxAge = 2 * 60
      r.addHeader("cache-control", "public, max-age=" + maxAge)
    } else {
      val maxStale = 2 * 24 * 60 * 60 // 2 days
      r.addHeader("cache-control", "public, only-if-cached, max-stale=" + maxStale)
    }

    // Set UserAgent
    r.addHeader("User-Agent", UA)

    return chain.proceed(r.build())
  }

  private fun isConnected(): Boolean {
    if (connectivityManager.activeNetworkInfo == null) {
      return false
    }
    return connectivityManager.activeNetworkInfo.isConnectedOrConnecting
  }
}
