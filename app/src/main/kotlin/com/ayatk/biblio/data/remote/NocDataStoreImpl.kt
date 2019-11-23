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

package com.ayatk.biblio.data.remote

import com.ayatk.biblio.infrastructure.database.entity.enums.RankingType
import com.ayatk.biblio.data.remote.entity.NarouEpisode
import com.ayatk.biblio.data.remote.entity.NarouIndex
import com.ayatk.biblio.data.remote.entity.NarouNovel
import com.ayatk.biblio.data.remote.entity.NarouRanking
import com.ayatk.biblio.data.remote.service.NarouApiService
import com.ayatk.biblio.data.remote.service.NarouService
import com.ayatk.biblio.data.remote.util.HtmlParser
import com.ayatk.biblio.di.scope.Nocturne
import io.reactivex.Flowable
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NocDataStoreImpl @Inject constructor(
  private val htmlParser: HtmlParser,
  private val narouApiService: NarouApiService,
  @Nocturne private val nocService: NarouService
) : NarouDataStore {

  override fun getNovel(query: Map<String, String>): Flowable<List<NarouNovel>> =
    narouApiService.getNovel18(query)
      // 0番目にall nullの要素が入ってしまってるのでdrop(1)しないと落ちる
      .map { novels -> novels.drop(1) }

  override fun getIndex(code: String): Flowable<List<NarouIndex>> =
    nocService.getTableOfContents(code.toLowerCase())
      .map { htmlParser.parseTableOfContents(code, it) }

  override fun getEpisode(code: String, page: Int): Flowable<NarouEpisode> =
    nocService.getPage(code, page)
      .map { htmlParser.parsePage(code, it, page) }

  override fun getShortStory(code: String): Flowable<NarouEpisode> =
    nocService.getSSPage(code)
      .map { htmlParser.parsePage(code, it, 1) }

  override fun getRanking(rankingType: RankingType, date: Date): Flowable<List<NarouRanking>> {
    TODO()
  }
}
