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

package com.ayatk.biblio.data.datasource.novel

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.NarouEpisode
import com.ayatk.biblio.domain.repository.EpisodeRepository
import com.ayatk.biblio.model.Episode
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.rx.toSingle
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodeRemoteDataSource
@Inject constructor(private val client: NarouClient) :
    EpisodeRepository {

  override fun find(novel: Novel, page: Int): Single<List<Episode>> {
    return if (novel.novelState == NovelState.SHORT_STORY) {
      client.getSSPage(novel.code)
          .map { listOf(convertEpisode(novel, it)) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    } else {
      client.getPage(novel.code, page)
          .map { listOf(convertEpisode(novel, it)) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }

  override fun save(episode: Episode): Completable {
    return Completable.create { /* no-op */ }
  }

  override fun deleteAll(novel: Novel): Single<Int> {
    return 0.toSingle()
  }

  private fun convertEpisode(novel: Novel, episode: NarouEpisode): Episode {
    return Episode(
        novel = novel,
        page = episode.page,
        subtitle = episode.subtitle,
        prevContent = episode.prevContent,
        content = episode.content,
        afterContent = episode.afterContent
    )
  }
}
