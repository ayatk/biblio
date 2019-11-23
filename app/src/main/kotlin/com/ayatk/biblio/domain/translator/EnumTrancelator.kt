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

package com.ayatk.biblio.domain.translator

import com.ayatk.biblio.model.enums.BigGenre
import com.ayatk.biblio.model.enums.Genre
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.model.enums.ReadingState
import com.ayatk.biblio.infrastructure.database.entity.enums.BigGenre as DataBigGenre
import com.ayatk.biblio.infrastructure.database.entity.enums.Genre as DataGenre
import com.ayatk.biblio.infrastructure.database.entity.enums.NovelState as DataNovelState
import com.ayatk.biblio.infrastructure.database.entity.enums.Publisher as DataPublisher
import com.ayatk.biblio.infrastructure.database.entity.enums.ReadingState as DataReadingState

// Publisher
fun DataPublisher.toModel() = Publisher.valueOf(name)

fun Publisher.toEntity() = DataPublisher.valueOf(name)

// BigGenre
fun DataBigGenre.toModel() = BigGenre.valueOf(name)

fun BigGenre.toEntity() = DataBigGenre.valueOf(name)

// Genre
fun DataGenre.toModel() = Genre.valueOf(name)

fun Genre.toEntity() = DataGenre.valueOf(name)

// NovelState
fun DataNovelState.toModel() = NovelState.valueOf(name)

fun NovelState.toEntity() = DataNovelState.valueOf(name)

// ReadingState
fun DataReadingState.toModel() = ReadingState.valueOf(name)

fun ReadingState.toEntity() = DataReadingState.valueOf(name)
