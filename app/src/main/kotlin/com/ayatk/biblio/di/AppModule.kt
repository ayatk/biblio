/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import android.content.Context
import android.net.ConnectivityManager
import com.ayatk.biblio.App
import com.ayatk.biblio.data.dao.OrmaDatabaseWrapper
import com.ayatk.biblio.data.narou.entity.enums.BigGenre
import com.ayatk.biblio.data.narou.entity.enums.Genre
import com.ayatk.biblio.data.narou.service.NarouApiService
import com.ayatk.biblio.data.narou.service.NarouService
import com.ayatk.biblio.data.util.RequestInterceptor
import com.ayatk.biblio.pref.DefaultPrefsWrapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(private val app: App) {

  @Provides
  fun provideApplicationContext(): Context = app

  @Provides
  fun provideConnectivityManager(): ConnectivityManager {
    return app.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
  }

  @Singleton
  @Provides
  fun provideDefaultPrefs(context: Context): DefaultPrefsWrapper = DefaultPrefsWrapper(
      context)

  @Provides
  fun provideUserAgentInterceptor(interceptor: RequestInterceptor): Interceptor = interceptor

  @Singleton
  @Provides
  fun provideOrmaDatabase(context: Context): OrmaDatabaseWrapper
      = OrmaDatabaseWrapper(context)

  @Singleton
  @Provides
  fun provideNarouApiService(client: OkHttpClient): NarouApiService {
    return Retrofit.Builder()
        .client(client)
        .baseUrl("http://api.syosetu.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(createGson()))
        .build()
        .create<NarouApiService>(NarouApiService::class.java)
  }

  @Singleton
  @Provides
  @Named("Narou")
  fun provideNarouService(client: OkHttpClient): NarouService {
    return Retrofit.Builder()
        .client(client)
        .baseUrl("http://ncode.syosetu.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create<NarouService>(NarouService::class.java)
  }

  @Singleton
  @Provides
  @Named("Narou18")
  fun provideNarou18Service(client: OkHttpClient): NarouService {
    return Retrofit.Builder()
        .client(client)
        .baseUrl("http://novel18.syosetu.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create<NarouService>(NarouService::class.java)
  }

  private fun createGson(): Gson {
    return GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .registerTypeAdapter(BigGenre::class.java,
            JsonDeserializer { jsonElement, _, _ -> BigGenre.of(jsonElement.asInt) })
        .registerTypeAdapter(Genre::class.java,
            JsonDeserializer { jsonElement, _, _ -> Genre.of(jsonElement.asInt) })
        .create()
  }
}
