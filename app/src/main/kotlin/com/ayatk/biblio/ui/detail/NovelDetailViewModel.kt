/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.detail

import android.databinding.BaseObservable
import android.util.Log
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.repository.novel.NovelBodyRepository
import com.ayatk.biblio.repository.novel.NovelTableRepository
import com.ayatk.biblio.ui.ViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NovelDetailViewModel @Inject constructor(
    private val novelBodyRepository: NovelBodyRepository,
    private val novelTableRepository: NovelTableRepository
) : BaseObservable(), ViewModel {

  lateinit var novel: Novel

  // TODO: 2017/10/27 プログレスを出したい
  fun downloadAll() {
    novelTableRepository.findAll(novel)
        .map { it.filter { !it.isChapter && !it.isDownload } }
        .map {
          // 1秒ごとに本文を取得する
          Observable.interval(1, TimeUnit.SECONDS)
              .zipWith(it, { _, value -> value })
              .map {
                novelBodyRepository.find(novel, it.page!!)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy(
                        onSuccess = { _ ->
                          novelTableRepository.save(listOf(it.copy(isDownload = true))).subscribe()
                        },
                        onError = {}
                    )
              }
              .subscribeBy(
                  onError = { t -> Log.e("NovelDetailViewModel", "", t) }
              )
        }
        .subscribeOn(Schedulers.io())
        .subscribeBy(
            onError = { Log.e("", "table: ", it) }
        )
  }

  override fun destroy() {}
}
