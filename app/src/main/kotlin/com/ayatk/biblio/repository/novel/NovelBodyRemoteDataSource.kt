/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.NarouNovelBody
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.util.toSingle
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelBodyRemoteDataSource
@Inject constructor(private val client: NarouClient) : NovelBodyDataSource {

  override fun find(novel: Novel, page: Int): Single<List<NovelBody>> {
    return if (novel.novelState == NovelState.SHORT_STORY) {
      client.getSSPage(novel.code)
          .map { listOf(convertNovelBody(novel, it)) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    } else {
      client.getPage(novel.code, page)
          .map { listOf(convertNovelBody(novel, it)) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
    }
  }

  override fun save(novelBody: NovelBody): Completable {
    return Completable.create { /* no-op */ }
  }

  override fun deleteAll(novel: Novel): Single<Int> {
    return 0.toSingle()
  }

  private fun convertNovelBody(novel: Novel, novelBody: NarouNovelBody): NovelBody {
    return NovelBody(
        novel = novel,
        page = novelBody.page,
        subtitle = novelBody.subtitle,
        prevContent = novelBody.prevContent,
        content = novelBody.content,
        afterContent = novelBody.afterContent
    )
  }
}
