/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
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
data class NovelBody(

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
