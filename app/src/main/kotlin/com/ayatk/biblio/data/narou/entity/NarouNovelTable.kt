/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.entity

import java.util.Date

data class NarouNovelTable(

    var ncode: String,

    var title: String,

    var isChapter: Boolean,

    var page: Int?,

    var publishDate: Date?,

    var lastUpdate: Date?
)
