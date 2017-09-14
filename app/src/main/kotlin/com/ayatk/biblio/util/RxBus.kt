/*
 * Copyright (c) 2016-2017. Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * RxJava EventBus
 * https://android.jlelse.eu/super-simple-event-bus-with-rxjava-and-kotlin-f1f969b21003
 */
object RxBus {
  private val publisher = PublishSubject.create<Any>()

  fun publish(event: Any) {
    publisher.onNext(event)
  }

  // Listen should return an Observable and not the publisher
  // Using ofType we filter only events that match that class type
  fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}
