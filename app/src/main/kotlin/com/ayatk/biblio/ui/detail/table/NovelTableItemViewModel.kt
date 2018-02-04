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

package com.ayatk.biblio.ui.detail.table

import android.content.Context
import android.databinding.BaseObservable
import com.ayatk.biblio.model.NovelTable
import com.ayatk.biblio.ui.ViewModel
import com.ayatk.biblio.ui.util.helper.Navigator
import com.ayatk.biblio.util.DateFormat

class NovelTableItemViewModel(val novelTable: NovelTable) : BaseObservable(), ViewModel {

  val lastUpdate: String =
      if (novelTable.publishDate == null) ""
      else DateFormat.yyyyMMddkkmm.format(novelTable.publishDate)

  override fun destroy() {}

  fun onItemClick(context: Context) {
    if (!novelTable.isChapter) {
      Navigator.navigateToNovelBody(context, novelTable.novel,
          requireNotNull(novelTable.page, { "ページ番号がはいってないぞい" })
      )
    }
  }
}
