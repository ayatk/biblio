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

package com.ayatk.biblio.data.remote.entity.mapper

import com.ayatk.biblio.data.entity.NovelEntity
import com.ayatk.biblio.data.entity.enums.Publisher
import com.ayatk.biblio.data.remote.entity.NarouNovel
import com.ayatk.biblio.domain.translator.toKeywordEntity
import com.ayatk.biblio.domain.translator.toKeywordModel
import com.ayatk.biblio.domain.translator.toModel
import com.ayatk.biblio.model.Novel

fun List<NarouNovel>.toEntity(publisher: Publisher): List<NovelEntity> =
  map {
    NovelEntity(
      code = it.ncode,
      title = it.title,
      userID = it.userID,
      writer = it.writer,
      story = it.story,
      publisher = publisher,
      bigGenre = it.bigGenre,
      genre = it.genre,
      keyword = it.keyword.toKeywordEntity(),
      novelState = it.novelType.toNovelState(it.end),
      firstUpload = it.firstup,
      lastUpload = it.lastup,
      page = it.page,
      length = it.length,
      readTime = it.time,
      isR18 = publisher == Publisher.NOCTURNE_MOONLIGHT,
      isR15 = it.isR15 == 1,
      isBL = it.isBL == 1,
      isGL = it.isGL == 1,
      isCruelness = it.isCruel == 1,
      isTransmigration = it.isAnotherWorld == 1,
      isTransfer = it.isTransfer == 1,
      globalPoint = it.allPoint,
      bookmarkCount = it.bookmarkCount,
      reviewCount = it.reviewCount,
      ratingCount = it.raterCount,
      illustrationCount = it.illustrationCount,
      conversationRate = it.conversationRate,
      novelUpdatedAt = it.novelUpdatedAt
    )
  }

fun List<NarouNovel>.toNovel(publisher: Publisher): List<Novel> =
  map {
    Novel(
      code = it.ncode,
      title = it.title,
      userID = it.userID,
      writer = it.writer,
      story = it.story,
      publisher = publisher.toModel(),
      bigGenre = it.bigGenre.toModel(),
      genre = it.genre.toModel(),
      keyword = it.keyword.toKeywordModel(),
      novelState = it.novelType.toNovelState(it.end).toModel(),
      firstUpload = it.firstup,
      lastUpload = it.lastup,
      page = it.page,
      length = it.length,
      readTime = it.time,
      isR18 = publisher == Publisher.NOCTURNE_MOONLIGHT,
      isR15 = it.isR15 == 1,
      isBL = it.isBL == 1,
      isGL = it.isGL == 1,
      isCruelness = it.isCruel == 1,
      isTransmigration = it.isAnotherWorld == 1,
      isTransfer = it.isTransfer == 1,
      point = it.allPoint,
      bookmarkCount = it.bookmarkCount,
      reviewCount = it.reviewCount,
      ratingCount = it.raterCount,
      illustrationCount = it.illustrationCount,
      conversationRate = it.conversationRate,
      novelUpdatedAt = it.novelUpdatedAt
    )
  }
