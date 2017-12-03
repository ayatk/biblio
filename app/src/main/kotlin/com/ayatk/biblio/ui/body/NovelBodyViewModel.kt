/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.body

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import com.ayatk.biblio.repository.novel.NovelBodyDataSource
import com.ayatk.biblio.ui.ViewModel
import javax.inject.Inject

class NovelBodyViewModel @Inject constructor(
    private val novelBodyDataSource: NovelBodyDataSource
) : BaseObservable(), ViewModel {

  @Bindable
  var novelBody: NovelBody = NovelBody()

  fun start(novel: Novel, page: Int) {
    novelBodyDataSource.find(novel, page)
        .subscribe(
            {
              novelBody = it.first()
              notifyPropertyChanged(BR.novelBody)
            },
            // TODO: 2017/11/26 エラーを握りつぶしマン
            {}
        )
  }

  override fun destroy() {}
}
