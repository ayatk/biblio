/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.model

import com.ayatk.biblio.model.enums.NovelState
import com.ayatk.biblio.model.enums.Publisher
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.OnConflict
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import org.parceler.Parcel
import org.parceler.Parcel.Serialization
import java.util.Date

@Parcel(Serialization.BEAN)
@Table
data class Novel(
    // TODO: 2017/04/24 やる気をださない

    @PrimaryKey(onConflict = OnConflict.REPLACE)
    @Setter("code")
    var code: String = "",

    @Column
    @Setter("title")
    var title: String = "",

    @Column
    @Setter("writer")
    var writer: String = "",

    @Column(indexed = true)
    @Setter("writerId")
    var writerId: String = "",

    @Column
    @Setter("story")
    var story: String = "",

    @Column(indexed = true)
    @Setter("publisher")
    var publisher: Publisher = Publisher.NAROU,

    @Column
    @Setter("novelTags")
    var novelTags: List<String> = listOf(),

    @Column
    @Setter("firstUpdateDate")
    var firstUpdateDate: Date = Date(),

    @Column
    @Setter("lastUpdateDate")
    var lastUpdateDate: Date = Date(),

    @Column
    @Setter("novelState")
    var novelState: NovelState = NovelState.SERIES,

    @Column
    @Setter("totalPages")
    var totalPages: Int = 0,

    @Column
    @Setter("allRateCount")
    var allRateCount: Int = 0,

    @Column
    @Setter("reviewCount")
    var reviewCount: Int = 0,

    @Column
    @Setter("bookmarkCount")
    var bookmarkCount: Int = 0,

    @Column
    @Setter("length")
    var length: Int = 0,

    @Column
    @Setter("original")
    var original: String? = "",

    @Column
    @Setter("isOrigin")
    var isOrigin: Boolean = true,

    @Column
    @Setter("isR15")
    var isR15: Boolean = false,

    @Column
    @Setter("isR18")
    var isR18: Boolean = false
)
