package com.ayatk.biblio.domain.translator

import com.ayatk.biblio.data.entity.NovelEntity
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel

fun NovelEntity.toNovel(): Novel = Novel(code, title)

fun NovelEntity.toLibrary(): Library = Library(code, Novel(code, title))

fun Novel.toNovelEntity(): NovelEntity = NovelEntity(code, title)
