/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.repository.novel

import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface NovelDataSource {

  fun findAll(codes: List<String>, publisher: Publisher): Single<List<Novel>>

  fun find(code: String, publisher: Publisher): Maybe<Novel>

  fun save(novel: Novel): Completable

  fun delete(code: String)
}
