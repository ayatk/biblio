/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import io.reactivex.Completable
import io.reactivex.Single

interface NovelBodyDataSource {

  fun find(novel: Novel, page: Int): Single<List<NovelBody>>

  fun save(novelBody: NovelBody): Completable

  fun deleteAll(novel: Novel): Single<Int>
}
