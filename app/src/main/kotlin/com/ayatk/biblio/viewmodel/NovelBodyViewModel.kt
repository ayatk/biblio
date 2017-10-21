/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import com.ayatk.biblio.repository.novel.NovelBodyRepository
import javax.inject.Inject

class NovelBodyViewModel
@Inject constructor(private val repository: NovelBodyRepository) : BaseObservable(), ViewModel {

  @Bindable
  var novelBody: NovelBody = NovelBody()

  fun start(novel: Novel, page: Int) {
    repository.find(novel, page)
        .subscribe(
            {
              novelBody = it.first()
              notifyPropertyChanged(BR.novelBody)
            },
            {}
        )
  }

  override fun destroy() {}
}
