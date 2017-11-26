/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.OrmaDatabase
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelLocalDataSource
@Inject constructor(private val orma: OrmaDatabase) : NovelDataSource {

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return orma.selectFromNovel()
        .publisherEq(publisher)
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun find(code: String, publisher: Publisher): Maybe<Novel> {
    return orma.selectFromNovel()
        .codeEq(code)
        .publisherEq(publisher)
        .executeAsObservable()
        .firstElement()
        .subscribeOn(Schedulers.io())
  }

  override fun save(novel: Novel): Completable {
    return orma.transactionAsCompletable {
      orma.relationOfNovel().upsert(novel)
    }.subscribeOn(Schedulers.io())
  }

  override fun delete(code: String) {
    orma.relationOfNovel()
        .deleter()
        .codeEq(code)
        .execute()
  }
}
