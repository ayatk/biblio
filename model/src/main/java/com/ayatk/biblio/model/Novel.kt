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

package com.ayatk.biblio.model

import com.ayatk.biblio.model.enums.BigGenre
import com.ayatk.biblio.model.enums.Genre
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import java.io.Serializable
import java.util.Date

data class Novel(

  val code: String,

  val title: String,

  val userID: Int = 0,

  val writer: String = "",

  val story: String = "",

  val publisher: Publisher = Publisher.NAROU,

  val bigGenre: BigGenre = BigGenre.OTHER,

  val genre: Genre = Genre.NON_GENRE,

  val keyword: List<String> = listOf(),

  val novelState: NovelState = NovelState.SERIES_END,

  val firstUpload: Date = Date(),

  val lastUpload: Date = Date(),

  val page: Int = 0,

  val length: Int = 0,

  val readTime: Int = 0,

  val isR18: Boolean = false,

  val isR15: Boolean = false,

  val isBL: Boolean = false,

  val isGL: Boolean = false,

  val isCruelness: Boolean = false,

  val isTransmigration: Boolean = false,

  val isTransfer: Boolean = false,

  val point: Int = 0,

  val bookmarkCount: Int = 0,

  val reviewCount: Int = 0,

  val ratingCount: Int = 0,

  val illustrationCount: Int = 0,

  val conversationRate: Int = 0,

  val novelUpdatedAt: Date = Date()
) : Serializable
