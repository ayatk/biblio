/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.data.dao

import android.content.Context
import com.ayatk.biblio.model.OrmaDatabase
import com.github.gfx.android.orma.AccessThreadConstraint

class OrmaDatabaseWrapper(context: Context) {
  val db: OrmaDatabase = OrmaDatabase.builder(context)
      .writeOnMainThread(AccessThreadConstraint.FATAL)
      .readOnMainThread(AccessThreadConstraint.FATAL)
      .build()
}
