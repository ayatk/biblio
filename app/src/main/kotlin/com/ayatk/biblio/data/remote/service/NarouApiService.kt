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

package com.ayatk.biblio.data.remote.service

import com.ayatk.biblio.data.remote.entity.NarouNovel
import com.ayatk.biblio.data.remote.entity.NarouRanking
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NarouApiService {

  @GET("/novelapi/api/")
  fun getNovel(@QueryMap query: Map<String, String>): Flowable<List<NarouNovel>>

  @GET("/novel18api/api/")
  fun getNovel18(@QueryMap query: Map<String, String>): Flowable<List<NarouNovel>>

  @GET("rank/rankget/?out=json")
  fun getRanking(@Query("rtype") rtype: String): Flowable<List<NarouRanking>>
}
