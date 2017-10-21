/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.data.dao.OrmaDatabaseWrapper
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelLocalDataSource
@Inject constructor(private val orma: OrmaDatabaseWrapper) : NovelDataSource {

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return orma.db.selectFromNovel()
        .publisherEq(publisher)
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun find(code: String, publisher: Publisher): Maybe<Novel> {
    return orma.db.selectFromNovel()
        .codeEq(code)
        .publisherEq(publisher)
        .executeAsObservable()
        .firstElement()
        .subscribeOn(Schedulers.io())
  }

  override fun save(novel: Novel): Completable {
    return orma.db.transactionAsCompletable {
      orma.db.relationOfNovel().upsert(novel)
    }.subscribeOn(Schedulers.io())
  }

  override fun delete(code: String) {
    orma.db.relationOfNovel()
        .deleter()
        .codeEq(code)
        .execute()
  }
}
