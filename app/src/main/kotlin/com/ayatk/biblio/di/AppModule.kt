/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.ayatk.biblio.data.narou.entity.enums.BigGenre
import com.ayatk.biblio.data.narou.entity.enums.Genre
import com.ayatk.biblio.data.narou.service.NarouApiService
import com.ayatk.biblio.data.narou.service.NarouService
import com.ayatk.biblio.data.narou.util.HtmlUtil
import com.ayatk.biblio.model.OrmaDatabase
import com.ayatk.biblio.pref.DefaultPrefs
import com.github.gfx.android.orma.AccessThreadConstraint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

  @Provides
  @Singleton
  fun provideApplicationContext(): Application = application

  @Provides
  fun provideConnectivityManager(application: Application): ConnectivityManager
      = application.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)

  @Singleton
  @Provides
  fun provideDefaultPrefs(application: Application): DefaultPrefs
      = DefaultPrefs.get(application)

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
  fun provideHtmlUtil(): HtmlUtil = HtmlUtil()

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
        .baseUrl("https://ncode.syosetu.com")
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
        .baseUrl("https://novel18.syosetu.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create<NarouService>(NarouService::class.java)
  }

  private fun createGson(): Gson {
    return GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .registerTypeAdapter(
            BigGenre::class.java,
            JsonDeserializer { jsonElement, _, _ -> BigGenre.of(jsonElement.asInt) }
        )
        .registerTypeAdapter(
            Genre::class.java,
            JsonDeserializer { jsonElement, _, _ -> Genre.of(jsonElement.asInt) }
        )
        .create()
  }
}
