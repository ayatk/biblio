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

package com.ayatk.biblio.data.remote.entity

import com.ayatk.biblio.data.entity.enums.BigGenre
import com.ayatk.biblio.data.entity.enums.Genre
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NarouNovel(

  /**
   * Nコード
   */
  @SerializedName("ncode")
  var ncode: String,

  /**
   * 小説名
   */
  @SerializedName("title")
  var title: String,

  /**
   * 作者のユーザID
   */
  @SerializedName("userid")
  var userID: Int,

  /**
   * 作者名
   */
  @SerializedName("writer")
  var writer: String,

  /**
   * 小説のあらすじ
   */
  @SerializedName("story")
  var story: String,

  /**
   * 大ジャンル
   * 詳細は[BigGenre]を参照
   */
  @SerializedName("biggenre")
  var bigGenre: BigGenre,

  /**
   * ジャンル
   * 詳細は[Genre]を参照
   */
  @SerializedName("genre")
  var genre: Genre,

  /**
   * 現在未使用項目(常に空文字列が返ります)
   */
  @SerializedName("gensaku")
  var gensaku: String,

  /**
   * キーワード
   */
  @SerializedName("keyword")
  var keyword: String,

  /**
   * 初回掲載時刻
   */
  @SerializedName("general_firstup")
  var firstup: Date,

  /**
   * 最終掲載時刻
   */
  @SerializedName("general_lastup")
  var lastup: Date,

  /**
   * 短編か連載かどうか
   * 連載の場合は1、短編の場合は2
   */
  @SerializedName("novel_type")
  var novelType: Int,

  /**
   * 小説が完結しているかどうか
   * 短編小説と完結済小説は0となっています。連載中は1です。
   */
  @SerializedName("end")
  var end: Int,

  /**
   * 	全掲載話数です。短編の場合は1です。
   */
  @SerializedName("general_all_no")
  var page: Int,

  /**
   * 小説文字数です。スペースや改行は文字数としてカウントしません。
   */
  @SerializedName("length")
  var length: Int,

  /**
   * 読了時間(分単位)です。読了時間は小説文字数÷500を切り上げした数値です。
   */
  @SerializedName("time")
  var time: Int,

  /**
   * 長期連載中は1、それ以外は0です。
   */
  @SerializedName("isstop")
  var isStop: Int,

  /**
   * 登録必須キーワードに「R15」が含まれる場合は1、それ以外は0です。
   */
  @SerializedName("isr15")
  var isR15: Int,

  /**
   * 登録必須キーワードに「ボーイズラブ」が含まれる場合は1、それ以外は0です。
   */
  @SerializedName("isbl")
  var isBL: Int,

  /**
   * 登録必須キーワードに「ガールズラブ」が含まれる場合は1、それ以外は0です。
   */
  @SerializedName("isgl")
  var isGL: Int,

  /**
   * 登録必須キーワードに「残酷な描写あり」が含まれる場合は1、それ以外は0です。
   */
  @SerializedName("iszankoku")
  var isCruel: Int,

  /**
   * 登録必須キーワードに「異世界転生」が含まれる場合は1、それ以外は0です
   */
  @SerializedName("istensei")
  var isAnotherWorld: Int,

  /**
   * 登録必須キーワードに「異世界転移」が含まれる場合は1、それ以外は0です。
   * ｢いずてんい｣とかダサくない...?
   */
  @SerializedName("istenni")
  var isTransfer: Int,

  /**
   * 1はケータイのみ、2はPCのみ、3はPCとケータイで投稿された作品です。
   * 対象は投稿と次話投稿時のみで、どの端末で執筆されたかを表すものではありません。
   */
  @SerializedName("pc_or_k")
  var pcOrMobile: Int,

  /**
   * 総合得点(=(ブックマーク数×2)+評価点)
   */
  @SerializedName("global_point")
  var globalPoint: Int,

  /**
   * ブックマーク数
   */
  @SerializedName("fav_novel_cnt")
  var bookmarkCount: Int,

  /**
   * レビュー数
   */
  @SerializedName("review_cnt")
  var reviewCount: Int,

  /**
   * 評価点
   */
  @SerializedName("all_point")
  var allPoint: Int,

  /**
   * 評価者数
   */
  @SerializedName("all_hyoka_cnt")
  var raterCount: Int,

  /**
   * 挿絵数
   */
  @SerializedName("sasie_cnt")
  var illustrationCount: Int,

  /**
   * 会話率
   */
  @SerializedName("kaiwaritu")
  var conversationRate: Int,

  /**
   * 小説の更新日時
   */
  @SerializedName("novelupdated_at")
  var novelUpdatedAt: Date,

  /**
   * 最終更新日時
   * (注意：システム用で小説更新時とは関係ありません)
   */
  @SerializedName("updated_at")
  var updatedAt: Date
)
