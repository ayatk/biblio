/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.NarouNovelTable
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelTable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelTableRemoteDataSource
@Inject constructor(val client: NarouClient) : NovelTableDataSource {

  override fun findAll(novel: Novel): Single<List<NovelTable>> {
    return client.getTableOfContents(novel.code)
        .map { convertNarouToModel(novel, it) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }

  override fun find(novel: Novel, page: Int): Maybe<NovelTable> {
    return findAll(novel)
        .toObservable()
        .flatMap { it.toObservable() }
        .filter { it.page == page }
        .singleElement()
  }

  override fun save(novelTables: List<NovelTable>): Completable {
    return Completable.create { /* no-op */ }
  }

  override fun delete(novel: Novel): Single<Int> {
    return Single.create { /* no-op */ }
  }

  private fun convertNarouToModel(
      novel: Novel, narouTables: List<NarouNovelTable>): List<NovelTable> {
    return narouTables.map {
      NovelTable(
          id = "${novel.code}-${it.id}",
          novel = novel,
          title = it.title,
          isChapter = it.isChapter,
          page = it.page,
          publishDate = it.publishDate,
          lastUpdate = it.lastUpdate
      )
    }
  }
}
