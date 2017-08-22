/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.NarouNovel
import com.ayatk.biblio.data.narou.util.QueryBuilder
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovelRemoteDataSource
@Inject constructor(private val client: NarouClient) : NovelDataSource {

  override fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>> {
    when (publisher) {
      Publisher.NAROU              -> {
        return client
            .getNovel(QueryBuilder().ncode(*codes.toTypedArray()).build())
            .map { convertNarouNovelToNovel(it, publisher) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
      }
      Publisher.NOCTURNE_MOONLIGHT -> {
        return client
            .getNovel18(QueryBuilder().ncode(*codes.toTypedArray()).build())
            .map { convertNarouNovelToNovel(it, publisher) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
      }
    }
  }

  override fun find(code: String, publisher: Publisher): Maybe<Novel> {
    return findAll(listOf(code), publisher)
        .toObservable()
        .flatMap { novels -> Observable.fromIterable(novels) }
        .singleElement()
  }

  override fun save(novel: Novel): Completable {
    return Completable.create { /* no-op */ }
  }

  override fun delete(code: String) {
    /* no-op */
  }

  private fun convertNarouNovelToNovel(
      narouNovels: List<NarouNovel>, publisher: Publisher): List<Novel> {
    return narouNovels.map {
      Novel(
          publisher = publisher,
          code = it.ncode,
          title = it.title,
          writer = it.writer,
          writerId = it.userID.toString(),
          story = it.story,
          novelTags = it.keyword.split(" "),
          firstUpdateDate = it.firstup,
          lastUpdateDate = it.lastup,
          novelState =
          if (it.novelType == 2) NovelState.SHORT_STORY
          else if (it.novelType == 1 && it.end == 1) NovelState.SERIES
          else NovelState.SERIES_END,
          totalPages = it.page,
          allRateCount = it.raterCount,
          reviewCount = it.reviewCount,
          bookmarkCount = it.bookmarkCount,
          length = it.length,
          original = it.gensaku,
          isOrigin = true,
          isR15 = it.isR15 == 1,
          isR18 = publisher == Publisher.NOCTURNE_MOONLIGHT
      )
    }
  }
}
