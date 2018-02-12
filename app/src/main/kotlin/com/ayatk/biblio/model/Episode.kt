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

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import org.parceler.Parcel
import org.parceler.Parcel.Serialization

@Parcel(Serialization.BEAN)
@Table
data class Episode(

    @PrimaryKey(autoincrement = true)
    @Setter("id")
    var id: Long = 0,

    @Column(indexed = true)
    @Setter("novel")
    val novel: Novel = Novel(),

    @Column(indexed = true)
    @Setter("page")
    val page: Int = 0,

    @Column
    @Setter("subtitle")
    val subtitle: String = "",

    @Column
    @Setter("prevContent")
    val prevContent: String = "",

    @Column
    @Setter("content")
    val content: String = "",

    @Column
    @Setter("afterContent")
    val afterContent: String = ""
)
