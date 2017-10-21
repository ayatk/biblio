/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.data.dao.OrmaDatabaseWrapper
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelTable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelTableLocalDataSource
@Inject constructor(val orma: OrmaDatabaseWrapper) : NovelTableDataSource {

  override fun findAll(novel: Novel): Single<List<NovelTable>> {
    return orma.db.selectFromNovelTable()
        .novelEq(novel)
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun find(novel: Novel, page: Int): Maybe<NovelTable> {
    return orma.db.selectFromNovelTable()
        .novelEq(novel)
        .page(page.toLong())
        .executeAsObservable()
        .firstElement()
        .subscribeOn(Schedulers.io())
  }

  override fun save(novelTables: List<NovelTable>): Completable {
    return orma.db.transactionAsCompletable {
      novelTables.map {
        orma.db.relationOfNovelTable().upsert(it)
      }
    }.subscribeOn(Schedulers.io())
  }

  override fun delete(novel: Novel): Single<Int> {
    return orma.db.relationOfNovelTable()
        .deleter()
        .novelEq(novel)
        .executeAsSingle()
        .subscribeOn(Schedulers.io())
  }
}
