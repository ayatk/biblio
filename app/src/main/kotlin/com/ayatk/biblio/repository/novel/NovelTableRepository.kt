/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.util.toSingle
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NovelTableRepository
@Inject constructor(
    private val localDataSource: NovelTableLocalDataSource,
    private val remoteDataSource: NovelTableRemoteDataSource
) : NovelTableDataSource {

  var isDirty = false

  override fun findAll(novel: Novel): Single<List<NovelTable>> {
    return if (isDirty) findAllFromRemote(novel) else findAllFromLocal(novel)
  }

  override fun find(novel: Novel, page: Int): Maybe<NovelTable> {
    return localDataSource.find(novel, page)
  }

  override fun save(novelTables: List<NovelTable>): Completable {
    return localDataSource.save(novelTables)
  }

  override fun delete(novel: Novel): Single<Int> {
    return localDataSource.delete(novel)
  }

  private fun findAllFromRemote(novel: Novel): Single<List<NovelTable>> {
    return remoteDataSource.findAll(novel)
        .doOnSuccess(this::updateAllAsync)
  }

  private fun findAllFromLocal(novel: Novel): Single<List<NovelTable>> {
    return localDataSource.findAll(novel)
        .flatMap {
          if (it.isEmpty()) {
            return@flatMap findAllFromRemote(novel)
          }
          return@flatMap it.toSingle()
        }
  }

  private fun updateAllAsync(novelTables: List<NovelTable>) {
    localDataSource.save(novelTables).subscribe()
    isDirty = false
  }
}
