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

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.ayatk.biblio.BR
import com.ayatk.biblio.model.Ranking
import com.ayatk.biblio.model.enums.Publisher
import com.ayatk.biblio.repository.ranking.RankingDataSource
import com.ayatk.biblio.ui.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RankingViewModel @Inject constructor(
    private val dataSource: RankingDataSource
) : BaseObservable(), ViewModel {

  @Bindable
  var daily: MutableList<Ranking> = mutableListOf()

  @Bindable
  var weekly: MutableList<Ranking> = mutableListOf()

  @Bindable
  var monthly: MutableList<Ranking> = mutableListOf()

  @Bindable
  var quarter: MutableList<Ranking> = mutableListOf()

  fun start() {
    dataSource.getDailyRank(Publisher.NAROU, 0 until 5)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          daily.clear()
          daily.addAll(ranks)
          notifyPropertyChanged(BR.daily)
        })

    dataSource.getWeeklyRank(Publisher.NAROU, 0 until 5)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          weekly.clear()
          weekly.addAll(ranks)
          notifyPropertyChanged(BR.weekly)
        })

    dataSource.getMonthlyRank(Publisher.NAROU, 0 until 5)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          monthly.clear()
          monthly.addAll(ranks)
          notifyPropertyChanged(BR.monthly)
        })

    dataSource.getQuarterRank(Publisher.NAROU, 0 until 5)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ ranks ->
          quarter.clear()
          quarter.addAll(ranks)
          notifyPropertyChanged(BR.quarter)
        })
  }

  override fun destroy() {}
}
