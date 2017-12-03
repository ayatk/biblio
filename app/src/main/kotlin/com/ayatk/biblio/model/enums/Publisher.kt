/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.model.enums

import android.support.annotation.StringRes
import com.ayatk.biblio.R

enum class Publisher(@StringRes val siteName: Int, val url: String) {
  // なろう
  NAROU(R.string.site_narou, "http://ncode.syosetu.com/"),

  // ノクターンとムーンライト
  NOCTURNE_MOONLIGHT(R.string.site_nocturne_moonlight, "http://novel18.syosetu.com/"),

  // ハーメルン
  // HAMELN("ハーメルン", "https://novel.syosetu.org/"),

  // カクヨム
  // KAKUYOMU("カクヨム", "https://kakuyomu.jp/works/");
  // TODO: 2017/04/24  もしかしたら他のもいれるかも
}
