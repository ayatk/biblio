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

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ayatk.biblio.domain.repository.NovelBodyRepository
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.NovelBody
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class NovelBodyViewModel @Inject constructor(
    private val novelBodyRepository: NovelBodyRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  var novelBody = MutableLiveData<NovelBody>()

  fun start(novel: Novel, page: Int) {
    novelBodyRepository.find(novel, page)
        .observeOn(schedulerProvider.ui())
        .subscribe(
            { novelBody.postValue(it.first()) },
            { Timber.e(it) }
        )
        .addTo(compositeDisposable)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
