/*
 * Copyright (c) 2016-2018 ayatk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ayatk.biblio.domain.usecase

import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface DetailUseCase {

  /**
   * この小説データは基本リモートから取ってくる。
   * リモートからとってきた場合は[Library]のidに "remote" の文字列が入ったインスタンスが返却される。
   *
   * @param novel 取得したい小説のデータ
   */
  fun getLibrary(novel: Novel): Single<Library>

  /**
   * これ気に入ったぜ!って小説があった時にローカルに保存する
   *
   * @param library 保存したいライブラリデータ
   */
  fun saveLibrary(library: Library): Completable

  /**
   * 目次一覧を取ってくる
   *
   * @param novel 欲しい小説のデータ
   */
  fun getIndex(novel: Novel): Flowable<List<Index>>

  /**
   * 目次を最新の状態にする
   *
   * @param code 最新の状態にしたい小説データ
   */
  fun updateIndex(novel: Novel): Completable
}
