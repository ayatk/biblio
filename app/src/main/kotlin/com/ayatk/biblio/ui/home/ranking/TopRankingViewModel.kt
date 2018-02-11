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

package com.ayatk.biblio.ui.home.ranking

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.ayatk.biblio.BR
import com.ayatk.biblio.data.narou.entity.enums.RankingType
import com.ayatk.biblio.domain.repository.RankingRepository
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.ui.ranking.RankingActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class TopRankingViewModel @Inject constructor(
    private val repository: RankingRepository
) : BaseObservable(), ViewModel {

  companion object {
    private const val TOP_RANK_RANGE = 5
  }

  private val compositeDisposable = CompositeDisposable()

  @Bindable
  var daily: MutableList<Ranking> = mutableListOf()

  @Bindable
  var weekly: MutableList<Ranking> = mutableListOf()

  @Bindable
  var monthly: MutableList<Ranking> = mutableListOf()

  @Bindable
  var quarter: MutableList<Ranking> = mutableListOf()

  @Bindable
  var all: MutableList<Ranking> = mutableListOf()

  fun start() {
    repository.getDailyRank(Publisher.NAROU, 0 until TOP_RANK_RANGE)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          daily.clear()
          daily.addAll(ranks)
          notifyPropertyChanged(BR.daily)
        })
        .addTo(compositeDisposable)

    repository.getWeeklyRank(Publisher.NAROU, 0 until TOP_RANK_RANGE)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          weekly.clear()
          weekly.addAll(ranks)
          notifyPropertyChanged(BR.weekly)
        })
        .addTo(compositeDisposable)

    repository.getMonthlyRank(Publisher.NAROU, 0 until TOP_RANK_RANGE)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          monthly.clear()
          monthly.addAll(ranks)
          notifyPropertyChanged(BR.monthly)
        })
        .addTo(compositeDisposable)

    repository.getQuarterRank(Publisher.NAROU, 0 until TOP_RANK_RANGE)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          quarter.clear()
          quarter.addAll(ranks)
          notifyPropertyChanged(BR.quarter)
        })
        .addTo(compositeDisposable)

    repository.getAllRank(Publisher.NAROU, 0..TOP_RANK_RANGE)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          all.clear()
          all.addAll(ranks)
          notifyPropertyChanged(BR.all)
        })
        .addTo(compositeDisposable)
  }

  fun onClickDailyRank(context: Context) {
    context.startActivity(RankingActivity.createIntent(context, RankingType.DAILY))
  }

  fun onClickWeeklyRank(context: Context) {
    context.startActivity(RankingActivity.createIntent(context, RankingType.WEEKLY))
  }

  fun onClickMonthlyRank(context: Context) {
    context.startActivity(RankingActivity.createIntent(context, RankingType.MONTHLY))
  }

  fun onClickQuarterRank(context: Context) {
    context.startActivity(RankingActivity.createIntent(context, RankingType.QUARTET))
  }

  fun onClickAllRank(context: Context) {
    context.startActivity(RankingActivity.createIntent(context, RankingType.ALL))
  }

  override fun destroy() {
    compositeDisposable.clear()
  }
}
