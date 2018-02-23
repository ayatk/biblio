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

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.ayatk.biblio.data.entity.enums.BigGenre
import com.ayatk.biblio.data.entity.enums.Genre
import com.ayatk.biblio.data.entity.enums.NovelState
import com.ayatk.biblio.data.entity.enums.Publisher
import java.util.Date

@Entity(tableName = "novel")
data class NovelEntity(

    @PrimaryKey
    @ColumnInfo(index = true)
    var code: String,

    var title: String,

    @ColumnInfo(name = "user_id", index = true)
    var userID: Int,

    var writer: String,

    var story: String,

    @ColumnInfo(index = true)
    var publisher: Publisher,

    @ColumnInfo(name = "big_genre")
    var bigGenre: BigGenre,

    @ColumnInfo(index = true)
    var genre: Genre,

    var keyword: String,

    @ColumnInfo(name = "novel_state", index = true)
    var novelState: NovelState,

    @ColumnInfo(name = "first_upload")
    var firstUpload: Date,

    @ColumnInfo(name = "last_upload")
    var lastUpload: Date,

    var page: Int,

    var length: Int,

    @ColumnInfo(name = "read_time")
    var readTime: Int,

    @ColumnInfo(name = "is_r18", index = true)
    var isR18: Boolean,

    @ColumnInfo(name = "is_r15", index = true)
    var isR15: Boolean,

    @ColumnInfo(name = "is_bl", index = true)
    var isBL: Boolean,

    @ColumnInfo(name = "is_gl", index = true)
    var isGL: Boolean,

    @ColumnInfo(name = "is_cruelness", index = true)
    var isCruelness: Boolean,

    @ColumnInfo(name = "is_transmigration", index = true)
    var isTransmigration: Boolean,

    @ColumnInfo(name = "is_transfer", index = true)
    var isTransfer: Boolean,

    @ColumnInfo(name = "global_point", index = true)
    var globalPoint: Int,

    @ColumnInfo(name = "bookmark_count")
    var bookmarkCount: Int,

    @ColumnInfo(name = "review_count")
    var reviewCount: Int,

    @ColumnInfo(name = "rating_count")
    var ratingCount: Int,

    @ColumnInfo(name = "illustration_count")
    var illustrationCount: Int,

    @ColumnInfo(name = "conversation_rate")
    var conversationRate: Int,

    @ColumnInfo(name = "novel_updated_at")
    var novelUpdatedAt: Date
)
