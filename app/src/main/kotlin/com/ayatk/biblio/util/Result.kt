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

package com.ayatk.biblio.util

sealed class Result<T>(val inProgress: Boolean) {

  class InProgress<T> : Result<T>(true)
  data class Success<T>(var data: T) : Result<T>(false)
  data class Failure<T>(val errorMessage: String?, val e: Throwable) : Result<T>(false)

  companion object {
    fun <T> inProgress(): Result<T> = InProgress()

    fun <T> success(data: T): Result<T> = Success(data)

    fun <T> failure(errorMessage: String, e: Throwable): Result<T> = Failure(errorMessage, e)
  }
}
