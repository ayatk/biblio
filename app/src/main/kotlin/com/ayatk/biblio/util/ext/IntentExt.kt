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

package com.ayatk.biblio.util.ext

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun Intent.extraOf(vararg pairs: Pair<String, Any>) = this.apply {
  for ((key, value) in pairs) {
    when (value) {
    // Scalars
      is Boolean -> putExtra(key, value)
      is Byte -> putExtra(key, value)
      is Char -> putExtra(key, value)
      is Double -> putExtra(key, value)
      is Float -> putExtra(key, value)
      is Int -> putExtra(key, value)
      is Long -> putExtra(key, value)
      is Short -> putExtra(key, value)

    // References
      is Bundle -> putExtra(key, value)
      is CharSequence -> putExtra(key, value)
      is Parcelable -> putExtra(key, value)
      is Serializable -> putExtra(key, value)

    // Scalar arrays
      is BooleanArray -> putExtra(key, value)
      is ByteArray -> putExtra(key, value)
      is CharArray -> putExtra(key, value)
      is DoubleArray -> putExtra(key, value)
      is FloatArray -> putExtra(key, value)
      is IntArray -> putExtra(key, value)
      is LongArray -> putExtra(key, value)
      is ShortArray -> putExtra(key, value)

      is ArrayList<*> -> {
        val componentType = value::class.java.componentType
        @Suppress("UNCHECKED_CAST") // Checked by reflection.
        when {
          Parcelable::class.java.isAssignableFrom(componentType) -> {
            putParcelableArrayListExtra(key, value as ArrayList<Parcelable>)
          }
          String::class.java.isAssignableFrom(componentType) -> {
            putStringArrayListExtra(key, value as ArrayList<String>)
          }
          CharSequence::class.java.isAssignableFrom(componentType) -> {
            putCharSequenceArrayListExtra(key, value as ArrayList<CharSequence>)
          }
          Int::class.java.isAssignableFrom(componentType) -> {
            putIntegerArrayListExtra(key, value as ArrayList<Int>)
          }
          else -> {
            val valueType = componentType.canonicalName
            throw IllegalArgumentException(
                "Illegal value array type $valueType for key \"$key\"")
          }
        }
      }
    }
  }
}
