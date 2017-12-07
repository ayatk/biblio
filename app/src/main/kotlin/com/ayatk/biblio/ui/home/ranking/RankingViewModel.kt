/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
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
