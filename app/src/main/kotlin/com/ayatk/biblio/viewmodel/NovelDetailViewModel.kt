/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import com.ayatk.biblio.model.Novel

class NovelDetailViewModel
constructor(val novel: Novel) : BaseObservable(), ViewModel {

  override fun destroy() {}
}
