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

package com.ayatk.biblio.domain.usecase

import com.ayatk.biblio.domain.repository.LibraryRepository
import com.ayatk.biblio.model.Library
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class HomeLibraryUseCaseImpl @Inject constructor(
    private val repository: LibraryRepository
) : HomeLibraryUseCase {

  override val libraries: Single<List<Library>> = repository.findAll()

  override fun delete(id: Long): Completable = repository.delete(id)
}
