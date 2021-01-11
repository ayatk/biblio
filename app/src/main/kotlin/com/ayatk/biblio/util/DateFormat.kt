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

package com.ayatk.biblio.util

import java.text.SimpleDateFormat
import java.util.*

enum class DatePattern(val pattern: String) {
  YYYY_MM_DD_KK_MM("yyyy/MM/dd kk:mm"),
  YYYY_MM_DD_KK_MM_JP("yyyy年MM月dd日 kk時mm分"),
}

fun String.purseDate(pattern: DatePattern): Date =
  SimpleDateFormat(pattern.pattern, Locale.getDefault()).parse(this)

fun Date.format(pattern: DatePattern): String =
  SimpleDateFormat(pattern.pattern, Locale.getDefault()).format(this)
