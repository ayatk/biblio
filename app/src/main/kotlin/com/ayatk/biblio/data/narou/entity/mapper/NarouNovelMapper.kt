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

package com.ayatk.biblio.data.narou.entity.mapper

import com.ayatk.biblio.data.narou.entity.NarouNovel
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher

fun List<NarouNovel>.toNovel(publisher: Publisher): List<Novel> =
    map {
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
          novelState = it.novelType.toNovelState(it.end),
          totalPages = it.page,
          allRateCount = it.raterCount,
          reviewCount = it.reviewCount,
          bookmarkCount = it.bookmarkCount,
          length = it.length,
          original = it.gensaku,
          isOrigin = true,
          isR15 = it.isR15 == 1,
          isR18 = publisher == Publisher.NOCTURNE_MOONLIGHT,
          point = it.globalPoint
      )
    }
