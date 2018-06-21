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

package com.ayatk.biblio.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
  tableName = "episode",
  foreignKeys = [
    ForeignKey(
      parentColumns = arrayOf("code"),
      childColumns = arrayOf("novel_code"),
      entity = NovelEntity::class
    ),
    ForeignKey(
      parentColumns = arrayOf("id"),
      childColumns = arrayOf("index_id"),
      entity = IndexEntity::class
    )
  ]
)
data class EpisodeEntity(

  /**
   * codeとpageとindexのIDをハイフンでつなぎ合わせてUUIDに変換したもの
   */
  @PrimaryKey
  var id: UUID,

  @ColumnInfo(name = "novel_code", index = true)
  var code: String,

  /**
   * codeとpageをハイフンでつなぎ合わせてUUIDに変換したもの
   */
  @ColumnInfo(name = "index_id", index = true)
  var indexId: UUID,

  @ColumnInfo(index = true)
  var page: Int,

  var subtitle: String,

  @ColumnInfo(name = "prev_content")
  var prevContent: String,

  var content: String,

  @ColumnInfo(name = "after_content")
  var afterContent: String
)
