/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelTable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface NovelTableDataSource {

  fun findAll(novel: Novel): Single<List<NovelTable>>

  fun find(novel: Novel, page: Int): Maybe<NovelTable>

  fun save(novelTables: List<NovelTable>): Completable

  fun delete(novel: Novel): Single<Int>
}
