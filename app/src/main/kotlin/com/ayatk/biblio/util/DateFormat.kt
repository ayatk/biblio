/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormat {
  @JvmStatic
  val yyyyMMddkkmm = SimpleDateFormat("yyyy/MM/dd kk:mm", Locale.getDefault())

  @JvmStatic
  val yyyyMMddkkmmJP = SimpleDateFormat("yyyy年MM月dd日 kk時mm分", Locale.getDefault())
}
