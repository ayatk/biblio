/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.library

import com.ayatk.biblio.data.dao.OrmaDatabaseWrapper
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.github.gfx.android.orma.annotation.OnConflict
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LibraryLocalDataSource
@Inject constructor(private val orma: OrmaDatabaseWrapper) : LibraryDataSource {

  override fun findAll(): Single<MutableList<Library>> {
    return orma.db.selectFromLibrary()
        .executeAsObservable()
        .toList()
        .subscribeOn(Schedulers.io())
  }

  override fun find(novel: Novel): Maybe<Library> {
    return orma.db.selectFromLibrary()
        .novelEq(novel)
        .executeAsObservable()
        .firstElement()
        .subscribeOn(Schedulers.io())
  }

  override fun save(library: Library): Completable {
    return orma.db.transactionAsCompletable {
      if (orma.db.relationOfNovel().codeEq(library.novel.code).isEmpty) {
        orma.db.relationOfNovel().inserter().execute(library.novel)
      }
      orma.db.relationOfLibrary().inserter(OnConflict.REPLACE).execute(library)
    }.subscribeOn(Schedulers.io())
  }

  override fun saveAll(libraries: List<Library>): Completable {
    return orma.db.transactionAsCompletable {
      libraries.forEach { library ->
        if (orma.db.relationOfNovel().selector().codeEq(library.novel.code).isEmpty) {
          orma.db.relationOfNovel().inserter().execute(library.novel)
        }
        orma.db.relationOfLibrary().inserter(OnConflict.REPLACE).execute(library)
      }
    }.subscribeOn(Schedulers.io())
  }

  override fun updateAllAsync(novels: List<Novel>) {
    orma.db.transactionAsCompletable {
      novels.forEach { orma.db.relationOfLibrary().upsert(Library(novel = it)) }
    }
        .subscribeOn(Schedulers.io())
        .subscribe()
  }

  override fun delete(novel: Novel): Single<Int> {
    return orma.db.relationOfLibrary()
        .deleter()
        .novelEq(novel)
        .executeAsSingle()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }
}
