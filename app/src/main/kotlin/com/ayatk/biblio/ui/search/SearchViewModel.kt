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

package com.ayatk.biblio.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ayatk.biblio.domain.usecase.SearchUseCase
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.ui.util.toResult
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.toLiveData
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  private val query = MutableLiveData<String>()

  val result: LiveData<Result<Map<Novel, Boolean>>> =
      Transformations.switchMap<String, Result<Map<Novel, Boolean>>>(query) { search ->
        useCase.search(search, Publisher.NAROU)
            .toResult(schedulerProvider)
            .toLiveData()
      }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  fun setQuery(originalInput: String) {
    val input = originalInput.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
    if (input != query.value && input.isNotBlank()) {
      query.value = input
    }
  }

  fun saveNovel(novel: Novel) =
      useCase.saveNovel(novel)
          .observeOn(schedulerProvider.ui())
          .subscribeBy(onError = { e -> Timber.e(e) })
          .addTo(compositeDisposable)
}
