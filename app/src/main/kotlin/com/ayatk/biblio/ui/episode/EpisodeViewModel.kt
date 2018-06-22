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

package com.ayatk.biblio.ui.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayatk.biblio.domain.usecase.EpisodeUseCase
import com.ayatk.biblio.model.Episode
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.util.toResult
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
  private val useCase: EpisodeUseCase,
  private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  val episode = MutableLiveData<Episode>()

  fun start(novel: Novel, page: Int) =
    useCase.getEpisode(novel, page)
      .toResult(schedulerProvider)
      .observeOn(schedulerProvider.ui())
      .subscribe { result ->
        when (result) {
          is Result.Success -> episode.postValue(result.data)
          is Result.Failure -> Timber.e(result.e)
        }
      }
      .addTo(compositeDisposable)

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }


}
