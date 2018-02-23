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

package com.ayatk.biblio.data.entity.mapper

import android.arch.persistence.room.TypeConverter
import com.ayatk.biblio.data.entity.enums.BigGenre
import com.ayatk.biblio.data.entity.enums.Genre
import com.ayatk.biblio.data.entity.enums.NovelState
import com.ayatk.biblio.data.entity.enums.Publisher
import java.util.Date

object Converters {

  @JvmStatic
  @TypeConverter
  fun serializeBigGenre(genre: BigGenre): String = genre.name

  @JvmStatic
  @TypeConverter
  fun deserializeBigGenre(genre: String): BigGenre = BigGenre.valueOf(genre)

  @JvmStatic
  @TypeConverter
  fun serializeGenre(genre: Genre): String = genre.name

  @JvmStatic
  @TypeConverter
  fun deserializeGenre(genre: String): Genre = Genre.valueOf(genre)

  @JvmStatic
  @TypeConverter
  fun serializeNovelType(state: NovelState): String = state.name

  @JvmStatic
  @TypeConverter
  fun deserializeNovelType(state: String): NovelState = NovelState.valueOf(state)

  @JvmStatic
  @TypeConverter
  fun serializePublisher(publisher: Publisher): String = publisher.name

  @JvmStatic
  @TypeConverter
  fun deserializePublisher(publisher: String): Publisher = Publisher.valueOf(publisher)

  @JvmStatic
  @TypeConverter
  fun deserializeDate(value: Long): Date = Date(value)

  @JvmStatic
  @TypeConverter
  fun serializeDate(value: Date): Long = value.time
}
