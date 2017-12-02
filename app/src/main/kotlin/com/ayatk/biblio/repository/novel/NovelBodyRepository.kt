/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle

class NovelBodyRepository(
    private val localDataSource: NovelBodyLocalDataSource,
    private val remoteDataSource: NovelBodyRemoteDataSource
) : NovelBodyDataSource {

  override fun find(novel: Novel, page: Int): Single<List<NovelBody>> {
    return localDataSource.find(novel, page)
        .flatMap {
          if (it.isEmpty()) {
            findToRemote(novel, page)
          }
          it.toSingle()
        }
  }

  override fun save(novelBody: NovelBody): Completable {
    return localDataSource.save(novelBody)
  }

  override fun deleteAll(novel: Novel): Single<Int> {
    return localDataSource.deleteAll(novel)
  }

  private fun findToRemote(novel: Novel, page: Int): Single<List<NovelBody>> {
    return remoteDataSource.find(novel, page)
        .doOnSuccess { save(it.first()).subscribe() }
  }
}
