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

package com.ayatk.biblio.ui.body

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import com.ayatk.biblio.repository.novel.NovelBodyRepository
import com.ayatk.biblio.ui.ViewModel
import javax.inject.Inject

class NovelBodyViewModel @Inject constructor(
    private val novelBodyRepository: NovelBodyRepository
) : BaseObservable(), ViewModel {

  @Bindable
  var novelBody: NovelBody = NovelBody()

  fun start(novel: Novel, page: Int) {
    novelBodyRepository.find(novel, page)
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
