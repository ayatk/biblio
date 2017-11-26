/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.model

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import java.util.Date

@Table
data class NovelTable(

    @PrimaryKey
    @Setter("id")
    var id: String = "",

    @Column(indexed = true)
    @Setter("novel")
    var novel: Novel,

    @Column
    @Setter("title")
    var title: String,

    @Column(indexed = true, defaultExpr = "false")
    @Setter("isDownload")
    val isDownload: Boolean = false,

    @Column(defaultExpr = "false")
    @Setter("isRead")
    val isRead: Boolean = false,

    @Column(indexed = true)
    @Setter("isChapter")
    var isChapter: Boolean,

    @Column(indexed = true)
    @Setter("page")
    var page: Int?,

    @Column
    @Setter("publishDate")
    var publishDate: Date?,

    @Column
    @Setter("lastUpdate")
    var lastUpdate: Date?
)
