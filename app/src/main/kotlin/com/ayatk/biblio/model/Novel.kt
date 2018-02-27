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
import org.parceler.Parcel
import org.parceler.Parcel.Serialization
import java.util.Date

@Parcel(Serialization.BEAN)
data class Novel(

    val code: String,

    val title: String,

    val userID: Int,

    val writer: String,

    val story: String,

    val publisher: Publisher,

    val bigGenre: BigGenre,

    val genre: Genre,

    val keyword: List<String>,

    val novelState: NovelState,

    val firstUpload: Date,

    val lastUpload: Date,

    val page: Int,

    val length: Int,

    val readTime: Int,

    val isR18: Boolean,

    val isR15: Boolean,

    val isBL: Boolean,

    val isGL: Boolean,

    val isCruelness: Boolean,

    val isTransmigration: Boolean,

    val isTransfer: Boolean,

    val point: Int,

    val bookmarkCount: Int,

    val reviewCount: Int,

    val ratingCount: Int,

    val illustrationCount: Int,

    val conversationRate: Int,

    val novelUpdatedAt: Date
)
