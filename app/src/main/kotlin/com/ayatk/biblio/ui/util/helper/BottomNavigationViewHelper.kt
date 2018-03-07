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

package com.ayatk.biblio.ui.util.helper

import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import timber.log.Timber

/**
 * In the case of BottomNavigationView has above 4 items,
 * the active tab width becomes too large. The cause is shiftingMode flag.
 * We can't access the flag and can't override the class. Then I hacked like this :(
 * http://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
 */
fun BottomNavigationView.disableShiftingMode() {
  val menuView = this.getChildAt(0) as BottomNavigationMenuView
  try {
    menuView.javaClass.getDeclaredField("mShiftingMode").apply {
      isAccessible = true
      setBoolean(menuView, false)
      isAccessible = false
    }
    for (i in 0 until menuView.childCount) {
      (menuView.getChildAt(i) as BottomNavigationItemView).also { item ->
        item.setShiftingMode(false)
        // Set once again checked value, so view will be updated
        item.setChecked(item.itemData.isChecked)
      }
    }
  } catch (e: NoSuchFieldException) {
    Timber.e("disableShiftingMode: Unable to get shift mode field")
  } catch (e: IllegalAccessException) {
    Timber.e("disableShiftingMode: Unable to change value of shift mode")
  }
}
