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

package com.ayatk.biblio.ui.ranking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.view.View
import com.ayatk.biblio.data.narou.entity.enums.RankingType
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.repository.ranking.RankingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RankingViewModel @Inject constructor(
    private val repository: RankingRepository
) : ViewModel() {

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  var rankingItemViewModels = ObservableArrayList<RankingItemViewModel>()

  var progressVisibility: MutableLiveData<Int> = MutableLiveData()

  // TODO: ここの処理が頭悪すぎて死ぬので絶対直す
  fun onCreate(rankingType: RankingType) {
    when (rankingType) {
      RankingType.DAILY -> repository.getDailyRank(Publisher.NAROU, 0 until RANK_SIZE)
          .map(this::convertToViewModel)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              this::renderLibraries,
              { throwable -> Timber.e(throwable, "Failed to show libraries.") }
          )
          .addTo(compositeDisposable)
      RankingType.WEEKLY -> repository.getWeeklyRank(Publisher.NAROU, 0 until RANK_SIZE)
          .map(this::convertToViewModel)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              this::renderLibraries,
              { throwable -> Timber.e(throwable, "Failed to show libraries.") }
          )
          .addTo(compositeDisposable)

      RankingType.MONTHLY -> repository.getMonthlyRank(Publisher.NAROU, 0 until RANK_SIZE)
          .map(this::convertToViewModel)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              this::renderLibraries,
              { throwable -> Timber.e(throwable, "Failed to show libraries.") }
          )
          .addTo(compositeDisposable)

      RankingType.QUARTET -> repository.getQuarterRank(Publisher.NAROU, 0 until RANK_SIZE)
          .map(this::convertToViewModel)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              this::renderLibraries,
              { throwable -> Timber.e(throwable, "Failed to show libraries.") }
          )
          .addTo(compositeDisposable)

      RankingType.ALL -> repository.getAllRank(Publisher.NAROU, 0..RANK_SIZE)
          .map(this::convertToViewModel)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              this::renderLibraries,
              { throwable -> Timber.e(throwable, "Failed to show libraries.") }
          )
          .addTo(compositeDisposable)
    }
  }

  private fun convertToViewModel(rankings: List<Ranking>): List<RankingItemViewModel> {
    return rankings.map { ranking -> RankingItemViewModel(ranking) }
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  private fun renderLibraries(rankingItemViewModels: List<RankingItemViewModel>) {
    progressVisibility.value = View.GONE
    if (this.rankingItemViewModels.size != rankingItemViewModels.size) {
      this.rankingItemViewModels.clear()
      this.rankingItemViewModels.addAll(rankingItemViewModels)
    }
  }

  companion object {
    private const val RANK_SIZE = 300
  }
}
