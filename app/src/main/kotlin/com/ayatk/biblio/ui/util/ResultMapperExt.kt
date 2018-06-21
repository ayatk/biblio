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

package com.ayatk.biblio.ui.util

import androidx.annotation.CheckResult
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@CheckResult
fun <T> Flowable<T>.toResult(schedulerProvider: SchedulerProvider): Flowable<Result<T>> =
  compose { item ->
    item.map { Result.success(it) }
      .onErrorReturn { e -> Result.failure(e.message ?: "unknown", e) }
      .observeOn(schedulerProvider.ui())
      .startWith(Result.inProgress())
  }

@CheckResult
fun <T> Observable<T>.toResult(schedulerProvider: SchedulerProvider): Observable<Result<T>> =
  compose { item ->
    item.map { Result.success(it) }
      .onErrorReturn { e -> Result.failure(e.message ?: "unknown", e) }
      .observeOn(schedulerProvider.ui())
      .startWith(Result.inProgress())
  }

@CheckResult
fun <T> Single<T>.toResult(schedulerProvider: SchedulerProvider): Observable<Result<T>> =
  toObservable().toResult(schedulerProvider)

@CheckResult
fun <T> Completable.toResult(schedulerProvider: SchedulerProvider): Observable<Result<T>> =
  toObservable<T>().toResult(schedulerProvider)
