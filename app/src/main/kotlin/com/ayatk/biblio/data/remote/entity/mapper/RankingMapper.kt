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

import com.ayatk.biblio.data.remote.entity.NarouRanking
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.Ranking

fun List<NarouRanking>.toRanking(novels: List<Novel>): List<Ranking> =
    map {
      Ranking(
          rank = it.rank,
          novel = novels.firstOrNull { novel -> novel.code == it.ncode } ?: unknownNovel,
          point = it.pt
      )
    }

fun List<Novel>.toRanking(): List<Ranking> =
    mapIndexed { index, novel ->
      Ranking(rank = index + 1, novel = novel, point = novel.point)
    }

private val unknownNovel = Novel(
    code = "unknown",
    title = "この小説は見ることができません"
)
