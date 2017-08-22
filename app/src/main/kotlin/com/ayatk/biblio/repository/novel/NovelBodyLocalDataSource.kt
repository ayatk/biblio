/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.data.dao.OrmaDatabaseWrapper
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelBodyLocalDataSource
@Inject constructor(private val orma: OrmaDatabaseWrapper) : NovelBodyDataSource {

  override fun find(novel: Novel, page: Int): Single<List<NovelBody>> {
    return orma.db.selectFromNovelBody()
        .novelEq(novel)
        .pageEq(page)
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun save(novelBody: NovelBody): Completable {
    return orma.db.transactionAsCompletable {
      orma.db.insertIntoNovelBody(novelBody)
    }.subscribeOn(Schedulers.io())
  }

  override fun deleteAll(novel: Novel): Single<Int> {
    return orma.db.deleteFromNovelBody()
        .novelEq(novel)
        .executeAsSingle()
        .subscribeOn(Schedulers.io())
  }
}
