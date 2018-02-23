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

    var code: String = "",

    var title: String = "",

    var userID: Int = 0,

    var writer: String = "",

    var story: String = "",

    var publisher: Publisher = Publisher.NAROU,

    var bigGenre: BigGenre = BigGenre.OTHER,

    var genre: Genre = Genre.OTHER,

    var keyword: List<String> = listOf(),

    var novelState: NovelState = NovelState.SERIES,

    var firstUpload: Date = Date(),

    var lastUpload: Date = Date(),

    var page: Int = 0,

    var length: Int = 0,

    var readTime: Int = 0,

    var isR18: Boolean = false,

    var isR15: Boolean = false,

    var isBL: Boolean = false,

    var isGL: Boolean = false,

    var isCruelness: Boolean = false,

    var isTransmigration: Boolean = false,

    var isTransfer: Boolean = false,

    var point: Int = 0,

    var bookmarkCount: Int = 0,

    var reviewCount: Int = 0,

    var ratingCount: Int = 0,

    var illustrationCount: Int = 0,

    var conversationRate: Int = 0,

    var novelUpdatedAt: Date = Date()
)
