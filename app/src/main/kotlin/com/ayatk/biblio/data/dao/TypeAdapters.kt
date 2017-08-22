/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.dao

import com.ayatk.biblio.data.narou.entity.enums.BigGenre
import com.ayatk.biblio.data.narou.entity.enums.Genre
import com.ayatk.biblio.data.narou.entity.enums.RankingType
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
        targetType = RankingType::class,
        serializedType = String::class,
        serializer = "serializeRankingType",
        deserializer = "deserializeRankingType"
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
) object TypeAdapters {
  @JvmStatic
  fun serializeBigGenre(genre: BigGenre): Int {
    return genre.type
  }

  @JvmStatic
  fun deserializeBigGenre(id: Int): BigGenre {
    return BigGenre.of(id)
  }

  @JvmStatic
  fun serializeGenre(genre: Genre): Int {
    return genre.type
  }

  @JvmStatic
  fun deserializeGenre(id: Int): Genre {
    return Genre.of(id)
  }

  @JvmStatic
  fun serializeRankingType(rankingType: RankingType): String {
    return rankingType.type
  }

  @JvmStatic
  fun deserializeRankingType(type: String): RankingType {
    return RankingType.valueOf(type)
  }

  @JvmStatic
  fun serializePublisher(publisher: Publisher): String {
    return publisher.name
  }

  @JvmStatic
  fun deserializePublisher(name: String): Publisher {
    return Publisher.valueOf(name)
  }

  @JvmStatic
  fun serializeNovelState(novelState: NovelState): String {
    return novelState.name
  }

  @JvmStatic
  fun deserializeNovelState(name: String): NovelState {
    return NovelState.valueOf(name)
  }
}
