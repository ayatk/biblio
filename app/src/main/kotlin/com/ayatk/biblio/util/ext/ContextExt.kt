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

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.BoolRes
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.v4.content.ContextCompat

fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.bool(@BoolRes boolRes: Int): Boolean = resources.getBoolean(boolRes)

fun Context.integer(@IntegerRes integerRes: Int): Int = resources.getInteger(integerRes)

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable =
    ContextCompat.getDrawable(this, drawableRes)!!
