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

package com.ayatk.biblio.data.db

import com.ayatk.biblio.model.Episode
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class EpisodeDatabaseImpl @Inject constructor(
    private val orma: OrmaDatabase
) : EpisodeDatabase {

  override fun find(novel: Novel, page: Int): Single<List<Episode>> =
      orma.selectFromEpisode()
          .novelEq(novel)
          .pageEq(page)
          .executeAsObservable()
          .toList()

  override fun save(episode: Episode): Completable =
      orma.transactionAsCompletable {
        orma.insertIntoEpisode(episode)
      }

  override fun delete(novel: Novel): Completable =
      orma.deleteFromEpisode()
          .novelEq(novel)
          .executeAsSingle()
          .toCompletable()
}
