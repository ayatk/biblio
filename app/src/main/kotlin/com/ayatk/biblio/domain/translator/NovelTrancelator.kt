package com.ayatk.biblio.domain.translator

import com.ayatk.biblio.data.entity.NovelEntity
import com.ayatk.biblio.model.Novel

fun NovelEntity.toModel(): Novel =
  Novel(
    code,
    title,
    userID,
    writer,
    story,
    publisher.toModel(),
    bigGenre.toModel(),
    genre.toModel(),
    keyword.toKeywordModel(),
    novelState.toModel(),
    firstUpload,
    lastUpload,
    page,
    length,
    readTime,
    isR18,
    isR15,
    isBL,
    isGL,
    isCruelness,
    isTransmigration,
    isTransfer,
    globalPoint,
    bookmarkCount,
    reviewCount,
    ratingCount,
    illustrationCount,
    conversationRate,
    novelUpdatedAt
  )

fun Novel.toEntity(): NovelEntity =
  NovelEntity(
    code,
    title,
    userID,
    writer,
    story,
    publisher.toEntity(),
    bigGenre.toEntity(),
    genre.toEntity(),
    keyword.toKeywordEntity(),
    novelState.toEntity(),
    firstUpload,
    lastUpload,
    page,
    length,
    readTime,
    isR18,
    isR15,
    isBL,
    isGL,
    isCruelness,
    isTransmigration,
    isTransfer,
    point,
    bookmarkCount,
    reviewCount,
    ratingCount,
    illustrationCount,
    conversationRate,
    novelUpdatedAt
  )
