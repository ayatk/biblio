/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.service

import com.ayatk.biblio.data.narou.entity.NarouNovel
import com.ayatk.biblio.data.narou.entity.NarouRanking
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NarouApiService {

  @GET("/novelapi/api/")
  fun getNovel(@QueryMap query: Map<String, String>): Single<List<NarouNovel>>

  @GET("/novel18api/api/")
  fun getNovel18(@QueryMap query: Map<String, String>): Single<List<NarouNovel>>

  @GET("rank/rankget/?out=json")
  fun getRanking(@Query("rtype") rtype: String): Single<List<NarouRanking>>
}
