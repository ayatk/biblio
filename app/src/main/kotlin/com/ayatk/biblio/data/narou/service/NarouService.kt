/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NarouService {

  @GET("/{ncode}")
  fun getTableOfContents(@Path("ncode") ncode: String): Single<String>

  @GET("/{ncode}/{page}")
  fun getPage(@Path("ncode") ncode: String, @Path("page") page: Int): Single<String>

  @GET("/{ncode}")
  fun getSSPage(@Path("ncode") ncode: String): Single<String>
}
