/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.model

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table
data class Bookmark(

    @PrimaryKey(autoincrement = true)
    @Setter("id")
    var id: Long = 0,

    @Column(indexed = true)
    @Setter("novel")
    var novel: Novel,

    @Column
    @Setter("novelTable")
    var novelTable: NovelTable
)
