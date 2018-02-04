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
