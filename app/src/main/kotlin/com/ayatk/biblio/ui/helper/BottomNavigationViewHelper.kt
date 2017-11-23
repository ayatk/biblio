/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.helper

import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log

object BottomNavigationViewHelper {

  private val TAG = BottomNavigationViewHelper::class.java.simpleName

  /**
   * In the case of BottomNavigationView has above 4 items,
   * the active tab width becomes too large. The cause is shiftingMode flag.
   * We can't access the flag and can't override the class. Then I hacked like this :(
   * http://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
   */
  fun disableShiftingMode(view: BottomNavigationView) {
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
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
      Log.e(TAG, "disableShiftingMode: Unable to get shift mode field")
    } catch (e: IllegalAccessException) {
      Log.e(TAG, "disableShiftingMode: Unable to change value of shift mode")
    }
  }
}
