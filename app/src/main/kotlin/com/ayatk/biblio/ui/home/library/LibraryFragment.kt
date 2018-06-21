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

package com.ayatk.biblio.ui.home.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProviders
import com.ayatk.biblio.R
import com.ayatk.biblio.data.DefaultPrefs
import com.ayatk.biblio.databinding.FragmentLibraryBinding
import com.ayatk.biblio.di.ViewModelFactory
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.home.library.item.LibraryItem
import com.ayatk.biblio.ui.util.helper.navigateToDetail
import com.ayatk.biblio.ui.util.init
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.observe
import com.ayatk.biblio.util.ext.setVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class LibraryFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  @Inject
  lateinit var defaultPrefs: DefaultPrefs

  private val viewModel: LibraryViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(LibraryViewModel::class.java)
  }

  lateinit var binding: FragmentLibraryBinding

  private val groupAdapter = GroupAdapter<ViewHolder>()
  private val onClickListener = { novel: Novel ->
    context!!.navigateToDetail(novel)
  }
  private val onMenuClickListener = { anchor: View, novel: Novel ->
    PopupMenu(context!!, anchor).apply {
      inflate(R.menu.menu_library_item)
      setOnMenuItemClickListener {
        when (it.itemId) {
          R.id.item_novel_delete -> {
            deleteDialog(novel)
            true
          }
          else -> false
        }
      }
      show()
    }
    false
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentLibraryBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)
    binding.viewModel = viewModel

    // 色設定
    binding.refresh.setColorSchemeResources(R.color.app_blue)

    binding.recyclerView.init(groupAdapter)

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.libraries.observe(this) { result ->
      when (result) {
        is Result.Success -> {
          val libraries = result.data
          groupAdapter.update(libraries.map {
            LibraryItem(it, defaultPrefs, onClickListener, onMenuClickListener)
          })
          binding.emptyView.setVisible(libraries.isEmpty())
          binding.recyclerView.setVisible(libraries.isNotEmpty())
        }
        is Result.Failure -> Timber.e(result.e)
      }
    }
  }

  private fun deleteDialog(novel: Novel) =
    AlertDialog.Builder(context!!)
      .setTitle(context!!.getString(R.string.delete_popup_title, novel.title))
      .setMessage(R.string.delete_popup_message)
      .setPositiveButton(R.string.delete_popup_positive) { _, _ ->
        viewModel.delete(novel)
      }
      .setNegativeButton(R.string.delete_popup_negative) { _, _ ->
        /* no-op */
      }
      .show()

  companion object {
    fun newInstance(): LibraryFragment = LibraryFragment()
  }
}
