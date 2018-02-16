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

package com.ayatk.biblio.data.db.dao

import com.ayatk.biblio.data.narou.entity.enums.BigGenre
import com.ayatk.biblio.data.narou.entity.enums.Genre
import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import com.github.gfx.android.orma.annotation.StaticTypeAdapter
import com.github.gfx.android.orma.annotation.StaticTypeAdapters

@StaticTypeAdapters(
    StaticTypeAdapter(
        targetType = BigGenre::class,
        serializedType = Int::class,
        serializer = "serializeBigGenre",
        deserializer = "deserializeBigGenre"
    ),
    StaticTypeAdapter(
        targetType = Genre::class,
        serializedType = Int::class,
        serializer = "serializeGenre",
        deserializer = "deserializeGenre"
    ),
    StaticTypeAdapter(
        targetType = Publisher::class,
        serializedType = String::class,
        serializer = "serializePublisher",
        deserializer = "deserializePublisher"
    ),
    StaticTypeAdapter(
        targetType = NovelState::class,
        serializedType = String::class,
        serializer = "serializeNovelState",
        deserializer = "deserializeNovelState"
    )
)
object TypeAdapters {
  @JvmStatic
  fun serializeBigGenre(genre: BigGenre): Int = genre.type

  @JvmStatic
  fun deserializeBigGenre(id: Int): BigGenre = BigGenre.of(id)

  @JvmStatic
  fun serializeGenre(genre: Genre): Int = genre.type

  @JvmStatic
  fun deserializeGenre(id: Int): Genre = Genre.of(id)

  @JvmStatic
  fun serializePublisher(publisher: Publisher): String = publisher.name

  @JvmStatic
  fun deserializePublisher(name: String): Publisher = Publisher.valueOf(name)

  @JvmStatic
  fun serializeNovelState(novelState: NovelState): String = novelState.name

  @JvmStatic
  fun deserializeNovelState(name: String): NovelState = NovelState.valueOf(name)
}
