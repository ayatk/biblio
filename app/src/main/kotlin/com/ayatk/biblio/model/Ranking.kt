/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.model

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class Ranking(

    var rank: Int = 0,

    var novel: Novel = Novel(),

    var point: Int = 0
)
