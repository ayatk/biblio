/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import android.support.annotation.VisibleForTesting
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.toMaybe
import io.reactivex.rxkotlin.toSingle
import java.util.LinkedHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NovelRepository
@Inject constructor(private val localDataSource: NovelLocalDataSource,
                    private val remoteDataSource: NovelRemoteDataSource) : NovelDataSource {

  @VisibleForTesting
  private var cache: MutableMap<String, Novel> = LinkedHashMap()

  var isDirty = false

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    if (hasCache() && !isDirty) {
      return ArrayList(cache.values).toSingle()
    }


    if (isDirty) {
      return findAllFromRemote(codes, publisher)
    } else {
      return findAllFromLocal(codes, publisher)
    }

  }

  override fun find(code: String, publisher: Publisher): Maybe<Novel> {
    if (hasCache(code)) {
      return cache[code].toMaybe()
    }

    if (isDirty) {
      return remoteDataSource.find(code, publisher)
          .doOnSuccess { novel -> updateAllAsync(listOf(novel)) }
    } else {
      return localDataSource.find(code, publisher)
    }
  }

  override fun save(novel: Novel): Completable {
    cache.put(novel.code, novel)
    return localDataSource.save(novel)
  }

  override fun delete(code: String) {
    localDataSource.delete(code)
  }

  private fun hasCache(): Boolean {
    return !cache.isEmpty()
  }

  private fun hasCache(code: String): Boolean {
    return cache.containsKey(code)
  }

  private fun findAllFromRemote(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return remoteDataSource.findAll(codes, publisher)
        .doOnSuccess({ novels ->
          refreshCache(novels)
          updateAllAsync(novels)
        })
  }

  private fun updateAllAsync(novels: List<Novel>) {
    novels.forEach { novel -> localDataSource.save(novel).subscribe() }
  }

  private fun findAllFromLocal(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    return localDataSource.findAll(codes, publisher)
        .flatMap { novels ->
          if (novels.isEmpty()) {
            return@flatMap findAllFromRemote(codes, publisher)
          }
          refreshCache(novels)
          return@flatMap novels.toSingle()
        }
  }

  private fun refreshCache(novels: List<Novel>) {
    cache.clear()
    novels.forEach { novel -> cache.put(novel.code, novel) }
    isDirty = false
  }
}
