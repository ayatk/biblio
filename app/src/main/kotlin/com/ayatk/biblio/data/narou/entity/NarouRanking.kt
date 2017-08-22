/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.narou.entity

import com.google.gson.annotations.SerializedName

data class NarouRanking(

    @SerializedName("rank")
    var rank: Int,

    @SerializedName("ncode")
    var ncode: String,

    @SerializedName("pt")
    var pt: Int
)
