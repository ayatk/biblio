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

import com.ayatk.biblio.infrastructure.database.entity.enums.BigGenre
import com.ayatk.biblio.infrastructure.database.entity.enums.Genre
import com.ayatk.biblio.data.remote.service.NarouApiService
import com.ayatk.biblio.data.remote.service.NarouService
import com.ayatk.biblio.di.scope.Narou
import com.ayatk.biblio.di.scope.Nocturne
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
import javax.inject.Singleton

@Module
class NetworkModule {
  @Singleton
  @Provides
  fun provideNarouApiService(client: OkHttpClient): NarouApiService =
    Retrofit.Builder()
      .client(client)
      .baseUrl("http://api.syosetu.com")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(createGson()))
      .build()
      .create<NarouApiService>(NarouApiService::class.java)

  @Singleton
  @Provides
  @Narou
  fun provideNarouService(client: OkHttpClient): NarouService =
    Retrofit.Builder()
      .client(client)
      .baseUrl("https://ncode.syosetu.com")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(ScalarsConverterFactory.create())
      .build()
      .create<NarouService>(NarouService::class.java)

  @Singleton
  @Provides
  @Nocturne
  fun provideNarou18Service(client: OkHttpClient): NarouService =
    Retrofit.Builder()
      .client(client)
      .baseUrl("https://novel18.syosetu.com")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(ScalarsConverterFactory.create())
      .build()
      .create<NarouService>(NarouService::class.java)

  private fun createGson(): Gson =
    GsonBuilder()
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
